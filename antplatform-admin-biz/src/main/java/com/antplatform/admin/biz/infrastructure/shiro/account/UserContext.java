package com.antplatform.admin.biz.infrastructure.shiro.account;

/**
 * @author: maoyan
 * @date: 2021/3/15 14:13:53
 * @description:
 */
public class UserContext implements AutoCloseable {

    static final ThreadLocal<CurrentUser> current = new ThreadLocal<>();

    public UserContext(CurrentUser user) {
        current.set(user);
    }

    public static CurrentUser getCurrentUser() {
        return current.get();
    }

    public static void setCurrentUser(CurrentUser user) {
        current.set(user);
    }

    @Override
    public void close() {
        current.remove();
    }
}
