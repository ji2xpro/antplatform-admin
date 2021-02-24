package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.antplatform.admin.common.base.Constants.SecurityConstant;
import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.RedisUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: maoyan
 * @date: 2021/2/8 19:48:26
 * @description:
 */
public class SystemLogoutFilter extends LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(SystemLogoutFilter.class);

//    @Autowired
//    private JedisUtils jedisUtils;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authorization = httpServletRequest.getHeader(SecurityConstant.REQUEST_AUTH_HEADER);
            String account = JwtUtil.getClaim(authorization);

            if(!StringUtils.isEmpty(account)){
                // 清除可能存在的Shiro权限信息缓存
                String tokenKey = SecurityConstant.PREFIX_SHIRO_CACHE + account;
                if (RedisUtil.hasKey(tokenKey)) {
                    RedisUtil.del(tokenKey);
                }
            }

            subject.logout();
        } catch (Exception ex) {
            logger.error("退出登录错误",ex);
        }

        this.writeResult(response);
        //不执行后续的过滤器
        return false;
    }

    private void writeResult(ServletResponse response){
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        //响应Json结果
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();

            AjaxResult ajaxResult = AjaxResult.createSuccessResult("退出成功");
            out.append(JSON.toJSONString(ajaxResult, SerializerFeature.WriteMapNullValue));
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
