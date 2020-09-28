package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.mapper.UserMapper;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.UserRoleMapper;
import com.antplatform.admin.biz.model.repository.UserRepository;
import com.antplatform.admin.biz.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2019/10/30 18:49:58
 * @description:
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UserRepository userRepository;

    /**
     * 查询用户信息
     *
     * @param userSpec
     * @return
     */
    @Override
    public User findBySpec(UserSpec userSpec) {
        return userRepository.findBySpec(userSpec);
    }
}

