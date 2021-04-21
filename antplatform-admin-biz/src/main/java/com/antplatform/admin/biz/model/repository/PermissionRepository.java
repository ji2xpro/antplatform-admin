package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.AuthorityMapper;
import com.antplatform.admin.biz.mapper.PermissionMapper;
import com.antplatform.admin.biz.mapper.RoleAuthorityMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/20 13:06:08
 * @description:
 */
@Component
public class PermissionRepository {
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    AuthorityMapper authorityMapper;

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    public Collection<Permission> findBySpec(RoleSpec roleSpec) {
//        RolePermission rolePermission = new RolePermission();
//        rolePermission.setRoleId(roleSpec.getRoleId());
//
//        List<RolePermission> rolePermissions = rolePermissionMapper.select(rolePermission);

        Example example = new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        if (roleSpec.getRoleId() != null){
            criteria.andEqualTo("roleId",roleSpec.getRoleId());
        }
        if (!CollectionUtils.isEmpty(roleSpec.getRoleIds())){
            criteria.andIn("roleId",roleSpec.getRoleIds());
        }
        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        return permissionMapper.selectByIdList(rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
    }

    public Collection<Authority> findBySpec1(RoleSpec roleSpec) {
        Example example = new Example(RoleAuthority.class);
        Example.Criteria criteria = example.createCriteria();
        if (roleSpec.getRoleId() != null){
            criteria.andEqualTo("roleId",roleSpec.getRoleId());
        }
        if (!CollectionUtils.isEmpty(roleSpec.getRoleIds())){
            criteria.andIn("roleId",roleSpec.getRoleIds());
        }
        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        return authorityMapper.selectByIdList(roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList()));
    }

    public Collection<Permission> findBySpec(PermissionSpec permissionSpec){
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();

        if (permissionSpec.getParentId() != null && permissionSpec.getParentId() >= 0){
            criteria.andEqualTo("parentId",permissionSpec.getParentId());
        }
        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<Permission> permissions = permissionMapper.selectByExample(example);

        Collection<Permission> permissionCollection = new ArrayList<>();
        for (Permission p: permissions) {
            PermissionSpec spec = new PermissionSpec();
            spec.setParentId(p.getId());

            permissionCollection.add(p);

            Collection<Permission> temp = findBySpec(spec);

            permissionCollection.addAll(temp);
        }
        return permissionCollection;
    }
}


