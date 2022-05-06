package com.antplatform.admin.biz.infrastructure.shiro.credentials;

import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.base.Constants.GlobalData;
import com.antplatform.admin.common.utils.RedisUtil;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.concurrent.TimeUnit;

/**
 * Shiro-密码输入错误的状态下重试次数的匹配管理
 *
 * @author: maoyan
 * @date: 2020/7/10 11:41:42
 * @description:
 */
public class RetryLimitCredentialsMatcher extends SimpleCredentialsMatcher {
    private static String loginCountKey;
    /**
     * 用户登录次数计数  redisKey 前缀
     */
    private static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
    /**
     * 用户登录是否被锁定    一小时 redisKey 前缀
     */
    private static final String SHIRO_IS_LOCK = "shiro_is_lock_";
    /**
     * 登录重试次数
     */
    private static final Integer MAX_RETRY_COUNT = 5;

    @Autowired
    private UserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (GlobalData.isLogin){
            GlobalData.isLogin = false;
            int retryCount = retryLimitCount(token, info);
            boolean matches = super.doCredentialsMatch(token, info);
            if (!matches) {
                String msg = retryCount <= 0 ? "您的账号一小时内禁止登录！" : "您还剩" + retryCount + "次重试的机会";
                throw new AccountException("帐号或密码不正确！" + msg);
            }

            //清空登录计数
            RedisUtil.del(loginCountKey);
            try {
//            userService.updateUserLastLoginInfo(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
       return super.doCredentialsMatch(token, info);
    }

    /**
     * 登录重试次数查询
     *
     * @param token
     * @param info
     * @return
     */
    private int retryLimitCount(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        // 访问一次，计数一次
        loginCountKey = SHIRO_LOGIN_COUNT + userName;
        String isLockKey = SHIRO_IS_LOCK + userName;
        RedisUtil.incr(loginCountKey, 1);

        if (RedisUtil.hasKey(isLockKey)) {
            throw new ExcessiveAttemptsException("帐号[" + userName + "]已被禁止登录！");
        }

        // 计数大于5时，设置用户被锁定一小时
        String loginCount = String.valueOf(RedisUtil.get(loginCountKey));
        int retryCount = (MAX_RETRY_COUNT - Integer.parseInt(loginCount));
        if (retryCount <= 0) {
            RedisUtil.set(isLockKey, "LOCK");
            RedisUtil.expire(isLockKey, 1, TimeUnit.HOURS);
            RedisUtil.expire(loginCountKey, 1, TimeUnit.HOURS);
            throw new ExcessiveAttemptsException("由于密码输入错误次数过多，帐号[" + userName + "]已被禁止登录！");
        }
        return retryCount;
    }
}
