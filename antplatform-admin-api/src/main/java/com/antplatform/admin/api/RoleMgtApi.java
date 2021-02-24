package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:43:06
 * @description:
 */
public interface RoleMgtApi {
    /**
     * 分页查询角色列表
     *
     * @param spec
     * @return
     */
    PagedResponse<RoleDTO> findPageBySpec(RolePageSpec spec);

    /**
     * 查询角色信息
     *
     * @param spec
     * @return
     */
    Response<RoleDTO> findBySpec(RoleSpec spec);

    /**
     * 新增或更新角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> saveOrUpdate(RoleSpec spec);

    /**
     * 新增角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> createRole(RoleSpec spec);

    /**
     * 更新角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> updateRole(RoleSpec spec);

    /**
     * 角色分配权限
     *
     * @param spec
     * @return
     */
    Response<Boolean> assignPermission(RolePermissionSpec spec);

    /**
     * 删除角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> delete(RoleSpec spec);
}
