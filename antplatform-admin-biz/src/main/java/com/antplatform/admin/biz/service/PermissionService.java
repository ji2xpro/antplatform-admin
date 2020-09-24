package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RolePermission;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:04:15
 * @description:
 */
public interface PermissionService {

    /**
     * 查询角色权限
     * @param roleSpec
     * @return
     */
    Collection<Permission> findBySpec(RoleSpec roleSpec);

    /**
     * 获取权限列表
     *
     * @param permissionListSpec
     * @return
     */
    Collection<Permission> findBySpec(PermissionListSpec permissionListSpec);


    /**
     * 获取权限列表
     *
     * @param permissionSpec
     * @return
     */
    Collection<Permission> findBySpec(PermissionSpec permissionSpec);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    Collection<RolePermission> findBySpec(int roleId, Integer isDeleteStatus);


}
