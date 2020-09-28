package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.infrastructure.SpringContextBean;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.base.component.JwtComponent;
import com.antplatform.admin.common.utils.JWTUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: maoyan
 * @date: 2020/7/28 20:01:59
 * @description: 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private UserService userService;

    /**
     * 判断用户是否想要登入。 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        setUserBean(request, response, token);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        response401(request, response);
        return false;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西 所以我们在这里返回true，Controller中可以通过
     * subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "Authorization"
        if (isLoginAttempt(request, response)) {
            try {
                // 如果存在，则进入 executeLogin 方法执行登录，检查 token 是否正确
                executeLogin(request, response);
                Subject subject = getSubject(request, response);
                String[] perms = (String[]) mappedValue;
                boolean isPermitted = true;
                if (perms != null && perms.length > 0)
                {
                    if (perms.length == 1)
                    {
                        if (!subject.isPermitted(perms[0]))
                        {
                            isPermitted = false;
                        }

                    } else if (!subject.isPermittedAll(perms))
                    {
                        isPermitted = false;
                    }

                }
                return isPermitted;
            } catch (Exception e) {
                e.printStackTrace();
                responseTimeOut(request, response);
            }
        }
        //如果请求头不存在 Authorization，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    private void setUserBean(ServletRequest request, ServletResponse response, JWTToken token) {
        if (this.userService == null) {
            this.userService = SpringContextBean.getBean(UserService.class);
        }
        String userAccount = JWTUtil.getUserAccount(String.valueOf(token.getCredentials()));

        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(userAccount);
        User userBean = userService.findBySpec(userSpec);
        request.setAttribute("currentUser", userBean);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        for (String urlMethod : Constant.METHOD_URL_SET) {
            String[] split = urlMethod.split(":");
            if (split[0].equals(httpServletRequest.getRequestURI())
                    && (split[1].equals(httpServletRequest.getMethod()) || "RequestMapping".equals(split[1]))) {
                return true;
            } else if (split[0].contains("{")) {
                String uri = split[0].substring(0, split[0].indexOf("{"));
                if (httpServletRequest.getRequestURI().startsWith(uri)) {
                    return true;
                }
            }
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/auth/401");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 超时跳转到 /timeout
     */
    private void responseTimeOut(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/auth/timeout");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
