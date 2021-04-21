package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:40:30
 * @description:
 */
public interface PermissionBiz {

    /**
     * 查询权限列表
     *
     * @param permissionSpec
     * @return
     */
    Response<List<PermissionDTO>> queryPermissions(PermissionSpec permissionSpec);

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    Response<Collection<PermissionDTO>> queryRolePermission(int roleId);
}
