package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: maoyan
 * @date: 2020/7/28 20:06:35
 * @description:
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = -2819620437297382200L;

    /**
     * 密钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
