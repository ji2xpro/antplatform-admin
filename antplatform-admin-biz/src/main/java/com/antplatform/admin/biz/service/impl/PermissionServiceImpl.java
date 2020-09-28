package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.PermissionMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.repository.PermissionRepository;
import com.antplatform.admin.biz.service.PermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:09:38
 * @description:
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 查询角色权限
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Collection<Permission> findBySpec(RoleSpec roleSpec) {
        Collection<Permission> permissions = permissionRepository.findBySpec(roleSpec);

//        return permissions.stream().filter(permission -> permission.getType() == 1 && permission.getIsDelete() == 0).collect(Collectors.toList());

        return permissions.stream().filter(permission -> permission.getIsDelete() == 0).collect(Collectors.toList());
    }

    /**
     * 获取权限列表
     *
     * @param permissionListSpec
     * @return
     */
    @Override
    public Collection<Permission> findBySpec(PermissionListSpec permissionListSpec) {
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(permissionListSpec.getIds())){
            criteria.andIn("id",permissionListSpec.getIds());
        }
        if (permissionListSpec.getParentId() > 0){
            criteria.andEqualTo("parentId",permissionListSpec.getParentId());
        }
        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<Permission> permissions = permissionMapper.selectByExample(example);
        return permissions;
    }

    /**
     * 获取权限列表
     *
     * @param permissionSpec
     * @return
     */
    @Override
    public Collection<Permission> findBySpec(PermissionSpec permissionSpec) {
//        Example example = new Example(Permission.class);
//        Example.Criteria criteria = example.createCriteria();
//
//        if (permissionSpec.getParentId() >= 0){
//            criteria.andEqualTo("parentId",permissionSpec.getParentId());
//        }
//        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());
//
//        List<Permission> permissions = permissionMapper.selectByExample(example);
//
//        Collection<Permission> permissionCollection = new ArrayList<>();
//        for (Permission p: permissions) {
//            PermissionSpec spec = new PermissionSpec();
//            spec.setParentId(p.getId());
//
//            permissionCollection = permissionRepository.findBySpec(spec);
//        }
//
//        permissions.addAll(permissionCollection);

        Collection<Permission> permissions = permissionRepository.findBySpec(permissionSpec);

        return permissions;
    }

    /**
     * 查询角色权限关联
     *
     * @param roleId
     * @return
     */
    @Override
    public Collection<RolePermission> findBySpec(int roleId,Integer isDeleteStatus) {
        Example example = new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();

        if(roleId > 0){
            criteria.andEqualTo("roleId",roleId);
        }
        if (isDeleteStatus != null){
            criteria.andEqualTo("isDelete",isDeleteStatus);
        }

        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);

        return rolePermissions;
    }
}
