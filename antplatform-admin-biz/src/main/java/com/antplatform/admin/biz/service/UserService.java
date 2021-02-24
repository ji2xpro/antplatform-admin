package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.common.dto.PageModel;

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

    /**
     * 分页查询用户列表
     *
     * @param spec
     * @return
     */
    PageModel findPageBySpec(UserPageSpec spec);
}
