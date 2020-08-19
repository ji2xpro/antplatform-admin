package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.biz.model.Permission;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:04:15
 * @description:
 */
public interface PermissionService {

    /**
     * 获取权限列表
     *
     * @param permissionListSpec
     * @return
     */
    Collection<Permission> findBySpec(PermissionListSpec permissionListSpec);
}
