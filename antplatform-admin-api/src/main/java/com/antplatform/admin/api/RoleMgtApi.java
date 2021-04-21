package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.RoleAuthSpec;
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
     * 保存角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> save(RoleSpec spec);

    /**
     * 角色分配权限
     *
     * @param spec
     * @return
     */
    Response<Boolean> assignPermission(RolePermissionSpec spec);

    /**
     * 角色分配权限
     *
     * @param spec
     * @return
     */
    Response<Boolean> assignAuth(RoleAuthSpec spec);

    /**
     * 删除角色信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> delete(RoleSpec spec);
}
