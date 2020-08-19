package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.UserRoleMapper;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.service.UserRoleService;
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
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;


    @Autowired
    RoleMapper roleMapper;

    /**
     * 查询用户角色
     *
     * @param userSpec
     * @return
     */
    @Override
    public Collection<Role> findBySpec(UserSpec userSpec) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userSpec.getUserId());

        List<UserRole> userRoles = userRoleMapper.select(userRole);

        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        return roleMapper.selectByIdList(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
    }
}
