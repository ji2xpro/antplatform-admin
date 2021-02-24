package com.antplatform.admin.common.base.Constants;

/**
 * @author: maoyan
 * @date: 2021/2/8 10:57:06
 * @description:
 */
public class SecurityConstant {
    public static final String LOGIN_SALT = "antplatform-admin";

    /**
     * request请求头属性
     */
    public static final String REQUEST_AUTH_HEADER = "Authorization";

    /**
     * JWT-account
     */
    public static final String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 组织ID
     */
    public static final String ORG_ID_TOKEN = "orgIdToken";

    /**
     * Shiro redis 前缀
     */
    public static final String PREFIX_SHIRO_CACHE = "antplatform-admin:cache:";

    /**
     * redis-key-前缀-shiro:refresh_token
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "antplatform-admin:refresh_token:";

    /**
     * redis-key-前缀-shiro:refresh_check
     */
    public final static String PREFIX_SHIRO_REFRESH_CHECK = "antplatform:refresh_check:";


}
