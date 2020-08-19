package com.antplatform.admin.biz.infrastructure.shiro.credentials;

import com.antplatform.admin.common.utils.AesUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Shiro-密码凭证匹配器（验证密码有效性）
 *
 * @author: maoyan
 * @date: 2020/7/10 11:39:22
 * @description:
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(uToken.getPassword());
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        try {
            dbPassword = AesUtil.decrypt(dbPassword, uToken.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}
