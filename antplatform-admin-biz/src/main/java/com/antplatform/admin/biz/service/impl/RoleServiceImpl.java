package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.UserRoleMapper;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.model.repository.RoleRepository;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.common.dto.PageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/11 16:06:45
 * @description:
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    UserRoleMapper userRoleMapper;


    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 查询用户角色
     *
     * @param userSpec
     * @return
     */
    @Override
    public Collection<Role> findBySpec(UserSpec userSpec) {
        UserRole userRole = new UserRole();
        if (userSpec.getUserId() > 0){
            userRole.setUserId(userSpec.getUserId());
        }

        List<UserRole> userRoles = userRoleMapper.select(userRole);

        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        return roleMapper.selectByIdList(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
    }

    /**
     * 分页查询角色列表
     *
     * @param spec
     * @return
     */
    @Override
    public PageModel findPageBySpec(RolePageSpec spec) {
        return roleRepository.findPageBySpec(spec);
    }

    /**
     * 查询角色信息
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Role findBySpec(RoleSpec roleSpec) {
        return roleRepository.findBySpec(roleSpec);
    }

    /**
     * 新增或更新角色信息
     *
     * @param role
     * @return
     */
    @Override
    public Boolean saveOrUpdate(Role role) {
        return roleRepository.createOrUpdate(role);
    }

    /**
     * 新增角色信息
     *
     * @param role
     * @return
     */
    @Override
    public Boolean create(Role role) {
        return roleRepository.createOrUpdate(role);
    }

    /**
     * 更新角色信息
     *
     * @param role
     * @return
     */
    @Override
    public Boolean update(Role role) {
        return roleRepository.createOrUpdate(role);
    }

    /**
     * 角色分配权限
     *
     * @param rolePermissions
     * @return
     */
    @Override
    public Boolean assignPermission(Collection<RolePermission> rolePermissions) {
//        Role role = roleMapper.selectByPrimaryKey(rolePermission.getRoleId());
//        if (StringUtils.isEmpty(role)) {
//            throw new BusinessException(ErrorCode.Role_Not_Found, "role not found with id:" + rolePermission.getRoleId());
//        }
        return roleRepository.assignPermission(rolePermissions);
    }
}
