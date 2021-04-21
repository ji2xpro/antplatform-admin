package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.RoleAuthSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/8/31 17:49:38
 * @description:
 */
public interface RoleBiz {
    /**
     * 分页查询角色列表
     *
     * @param rolePageSpec
     * @return
     */
    PagedResponse<RoleDTO> queryRolePage(RolePageSpec rolePageSpec);

    /**
     * 查询角色信息
     *
     * @param roleSpec
     * @return
     */
    Response<RoleDTO> queryRole(RoleSpec roleSpec);

    /**
     * 保存角色信息
     *
     * @param roleSpec
     * @return
     */
    Response<Boolean> save(RoleSpec roleSpec);

    /**
     * 保存角色权限
     *
     * @param rolePermissionSpec
     * @return
     */
    Response<Boolean> saveRolePermission(RolePermissionSpec rolePermissionSpec);


    /**
     * 保存角色权限
     *
     * @param roleAuthSpec
     * @return
     */
    Response<Boolean> saveRoleAuth(RoleAuthSpec roleAuthSpec);

    /**
     * 删除角色信息
     *
     * @param roleSpec
     * @return
     */
    Response<Boolean> delete(RoleSpec roleSpec);


}
