package com.antplatform.admin.biz.infrastructure.shiro.credentials;

import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Shiro-凭证匹配器（验证JwtToken有效性）
 *
 * @author: maoyan
 * @date: 2020/7/10 11:39:22
 * @description:
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * CredentialsMatcher只需验证JwtToken内容是否合法
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String jwtToken = token.getPrincipal().toString();
        String password = info.getCredentials().toString();
        String username = JwtUtil.getClaim(jwtToken);
        return JwtUtil.verify(jwtToken, username, password);
    }
}
