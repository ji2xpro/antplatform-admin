package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.User;

/**
 * @author: maoyan
 * @date: 2020/1/19 16:37:52
 * @description:
 */
public interface UserService{
    /**
     * 查询用户信息
     * @param userSpec
     * @return
     */
    User findBySpec(UserSpec userSpec);
}
