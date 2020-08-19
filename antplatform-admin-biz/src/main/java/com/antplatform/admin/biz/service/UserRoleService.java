package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Role;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 16:06:27
 * @description:
 */
public interface UserRoleService {
    /**
     * 查询用户角色
     * @param userSpec
     * @return
     */
    Collection<Role> findBySpec(UserSpec userSpec);
}
