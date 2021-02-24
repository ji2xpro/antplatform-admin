package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.common.dto.PageModel;
import com.antplatform.admin.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/11 16:06:27
 * @description:
 */
public interface RoleService {
    /**
     * 查询用户角色
     *
     * @param userSpec
     * @return
     */
    Collection<Role> findBySpec(UserSpec userSpec);

    /**
     * 分页查询角色列表
     *
     * @param spec
     * @return
     */
    PageModel findPageBySpec(RolePageSpec spec);

    /**
     * 查询角色信息
     *
     * @param roleSpec
     * @return
     */
    Role findBySpec(RoleSpec roleSpec);

    /**
     * 新增或更新角色信息
     *
     * @param role
     * @return
     */
    Boolean saveOrUpdate(Role role);

    /**
     * 新增角色信息
     *
     * @param role
     * @return
     */
    Boolean create(Role role);

    /**
     * 更新角色信息
     *
     * @param role
     * @return
     */
    Boolean update(Role role);

    /**
     * 角色分配权限
     * @param rolePermissions
     * @return
     */
    Boolean assignPermission(Collection<RolePermission> rolePermissions);
}
