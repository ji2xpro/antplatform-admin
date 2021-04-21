package com.antplatform.admin.biz.infrastructure.shiro.account;

import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtUtil;
import com.antplatform.admin.common.base.Constants.SecurityConstant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: maoyan
 * @date: 2021/3/15 14:10:39
 * @description:
 */
@WebFilter(filterName = "userContextFilter",urlPatterns = "/*")
public class UserContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authorization = httpServletRequest.getHeader(SecurityConstant.REQUEST_AUTH_HEADER);
        if(authorization!=null){
            String account = JwtUtil.getClaim(authorization, SecurityConstant.ACCOUNT);
            CurrentUser currentUser = new CurrentUser(account);
            try (UserContext context = new UserContext(currentUser)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
