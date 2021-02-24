package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.infrastructure.SpringContextBean;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.biz.service.common.SyncCacheService;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.base.Constants.SecurityConstant;
import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.RedisUtil;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * JwtFilter:jwt过滤器来作为shiro的过滤器
 *
 * @author: maoyan
 * @date: 2020/7/28 20:01:59
 * @description: 代码的执行流程 {@link #preHandle} -> {@link #isAccessAllowed} -> {@link #isLoginAttempt} -> {@link #executeLogin}
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private SyncCacheService syncCacheService;

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("JwtFilter-->>>preHandle-Method:init()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        // 若请求的路径属于METHOD_URL_SET, 则直接返回True无需拦截; 反之不属于METHOD_URL_SET, 则会执行super.preHandle(request, response);
        String path = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        for (String urlMethod : Constant.IGNORE_PATH_SET) {
            String[] split = urlMethod.split(":");

            if (split[0].equals(path) && (split[1].equals(method) || "RequestMapping".equals(split[1]))) {
                return true;
            } else if (split[0].contains("{")) {
                String uri = split[0].substring(0, split[0].indexOf("{"));
                if (path.startsWith(uri)) {
                    return true;
                }
            }
        }
        return super.preHandle(request, response);
    }

    /**
     * 是否允许访问
     * <p>
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西 所以我们在这里返回true，Controller中可以通过
     * subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return {@code true}, 继续执行
     * {@code false}, 则会调用{@link #onAccessDenied}方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.info("JwtFilter-->>>isAccessAllowed-Method:init()");
        //判断请求的请求头是否带上 "Authorization"
        if (isLoginAttempt(request, response)) {
            try {
                // 如果存在，则进入 executeLogin 方法执行登录，检查 token 是否正确
                executeLogin(request, response);
                Subject subject = getSubject(request, response);
                String[] perms = (String[]) mappedValue;
                boolean isPermitted = true;
                if (perms != null && perms.length > 0) {
                    if (perms.length == 1) {
                        if (!subject.isPermitted(perms[0])) {
                            isPermitted = false;
                        }

                    } else if (!subject.isPermittedAll(perms)) {
                        isPermitted = false;
                    }

                }
                return isPermitted;
            } catch (Exception e) {
                logger.error("登录验证失败!", e);
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = String.format("Token或者密钥不正确(%s)", throwable.getMessage());
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    msg = String.format("Token已过期(%s)", throwable.getMessage());
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                this.response401(response, msg);
//                responseUnauthorized(request, response, e.getMessage());
//                return false;
            }
        }
        //如果请求头不存在 Authorization，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        logger.info("token认证失败");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        AjaxResult ajaxResult = AjaxResult.createFailedResult(AjaxCode.UNLOGIN_CODE, "登录失效，请重新登录");
        response.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult, SerializerFeature.WriteMapNullValue));
        return false;
    }

    /**
     * 判断用户是否想要登入, 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        logger.info("JwtFilter-->>>isLoginAttempt-Method:init()");
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConstant.REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    /**
     * 重写AuthenticatingFilter的executeLogin方法, 执行登陆操作
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("JwtFilter-->>>executeLogin-Method:init()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(SecurityConstant.REQUEST_AUTH_HEADER);
        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入, 如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        //检查是否需要更换token，需要则重新颁发
        this.refreshTokenIfNeed(request, response);
        // 如果没有抛出异常则代表登入成功，返回true
        setUserBean(request, response, token);
        return true;
    }

    /**
     * 检查是否需要,若需要则校验时间戳，刷新Token，并更新时间戳
     *
     * @param request
     * @param response
     * @return
     */
    private boolean refreshTokenIfNeed(ServletRequest request, ServletResponse response) {
        // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
        String authorization = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtil.getClaim(authorization);
        long currentTimeMillis = System.currentTimeMillis();
        //检查刷新规则
        if (this.refreshCheck(authorization, currentTimeMillis)) {
            String lockName = SecurityConstant.PREFIX_SHIRO_REFRESH_CHECK + account;
            boolean b = syncCacheService.getLock(lockName, Constant.ExpireTime.ONE_MINUTE);
            if (b) {
                //获取到锁
                String refreshTokenKey = SecurityConstant.PREFIX_SHIRO_REFRESH_TOKEN + account;
                // 判断Redis中RefreshToken是否存在
                if (RedisUtil.hasKey(refreshTokenKey)) {
                    String tokenTimeStamp = RedisUtil.get(refreshTokenKey).toString();
                    String tokenMillis = JwtUtil.getClaim(authorization, SecurityConstant.CURRENT_TIME_MILLIS);
                    //检查redis中的时间戳与token的时间戳是否一致
                    if (!tokenMillis.equals(tokenTimeStamp)) {
                        throw new TokenExpiredException(String.format("账户%s的令牌无效", account));
                    }
                }

                UserSpec userSpec = new UserSpec();
                userSpec.setUsername(account);
                User user = userService.findBySpec(userSpec);
                //时间戳一致，则颁发新的令牌
                String newToken = JwtUtil.genToken(account, user.getPassword(), Constant.ExpTimeType.WEB, currentTimeMillis);

                // 设置响应的Header头新Token
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(SecurityConstant.REQUEST_AUTH_HEADER, newToken);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConstant.REQUEST_AUTH_HEADER);
            }
            syncCacheService.releaseLock(lockName);
        }
        return true;
    }

    /**
     * 检查是否需要更新Token
     *
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private boolean refreshCheck(String authorization, Long currentTimeMillis) {
        String tokenMillis = JwtUtil.getClaim(authorization, SecurityConstant.CURRENT_TIME_MILLIS);
        if (currentTimeMillis - Long.parseLong(tokenMillis) > (jwtProperties.refreshCheckTime * 60 * 1000L)) {
            return true;
        }
        return false;
    }


    private void setUserBean(ServletRequest request, ServletResponse response, JwtToken token) {
        if (this.userService == null) {
            this.userService = SpringContextBean.getBean(UserService.class);
        }
        String userAccount = JwtUtil.getClaim(String.valueOf(token.getCredentials()));

        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(userAccount);
        User userBean = userService.findBySpec(userSpec);
        request.setAttribute("currentUser", userBean);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletResponse response, String msg) {
//        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");

        ServletOutputStream output = null;
        try {
            output = httpServletResponse.getOutputStream();
            AjaxResult ajaxResult = AjaxResult.createFailedResult(AjaxCode.UNLOGIN_CODE, msg);
            output.write(JSON.toJSONString(ajaxResult, SerializerFeature.WriteMapNullValue).getBytes(StandardCharsets.UTF_8));

            output.flush();
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseUnauthorized(ServletRequest request, ServletResponse response, String message) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String contextPath = httpServletRequest.getContextPath();

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect(contextPath + "/unauthorized/" + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
