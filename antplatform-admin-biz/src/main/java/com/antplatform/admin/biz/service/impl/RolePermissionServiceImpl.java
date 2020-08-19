package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.PermissionMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/12 16:32:44
 * @description:
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 查询角色权限
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Collection<Permission> findBySpec(RoleSpec roleSpec) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleSpec.getRoleId());

        List<RolePermission> rolePermissions = rolePermissionMapper.select(rolePermission);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        return permissionMapper.selectByIdList(rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
    }
}
