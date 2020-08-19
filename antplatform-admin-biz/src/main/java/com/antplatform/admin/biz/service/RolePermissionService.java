package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Permission;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/12 16:29:10
 * @description:
 */
public interface RolePermissionService {

    /**
     * 查询角色权限
     * @param roleSpec
     * @return
     */
    Collection<Permission> findBySpec(RoleSpec roleSpec);
}
