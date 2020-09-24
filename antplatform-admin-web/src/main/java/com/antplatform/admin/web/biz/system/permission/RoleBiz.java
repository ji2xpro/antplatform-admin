package com.antplatform.admin.web.biz.system.permission;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 17:49:38
 * @description:
 */
public interface RoleBiz {
    /**
     * 查询角色列表
     *s
     * @param rolePageSpec
     * @return
     */
    PagedResponse<RoleDTO> queryRoles(RolePageSpec rolePageSpec);

    /**
     * 查询角色信息
     * @param roleSpec
     * @return
     */
    Response<RoleDTO> queryRole(RoleSpec roleSpec);

    /**
     * 保存角色信息
     * @param roleSpec
     * @return
     */
    Response<Boolean> saveRole(RoleSpec roleSpec);


    /**
     * 保存角色权限
     * @param rolePermissionSpec
     * @return
     */
    Response<Boolean> saveRolePermission(RolePermissionSpec rolePermissionSpec);

    /**
     * 删除角色信息
     * @param roleSpec
     * @return
     */
    Response<Boolean> delete(RoleSpec roleSpec);







}
