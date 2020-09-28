package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:43:06
 * @description:
 */
public interface PermissionMgtApi {
    /**
     * 查询权限列表
     * @param spec
     * @return
     */
    Response<List<PermissionDTO>> findBySpec(PermissionSpec spec);

    /**
     * 查询角色权限列表
     * @param roleId
     * @return
     */
    Response<Collection<PermissionDTO>> findBySpec(int roleId);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    Response<Collection<RolePermissionDTO>> findBySpec1(int roleId);
}
