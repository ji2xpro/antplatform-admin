package com.antplatform.admin.biz.service;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.biz.mapper.UserMapper;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.UserRoleMapper;
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

    /**
     * 查询指定用户
     *
     * @param userMgtSpec
     * @return
     */
    @Override
    public User getUser(UserMgtSpec userMgtSpec) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userMgtSpec.getUsername());
        criteria.andEqualTo("password",userMgtSpec.getPassword());
        return userMapper.selectOneByExample(example);
    }

    /**
     * 查询用户信息
     * @param userMgtSpec
     * @return
     */
    @Override
    public UserDTO getUserRole(UserMgtSpec userMgtSpec) {
        String token = userMgtSpec.getToken();
        if(StringUtils.isNoneBlank(token)){
            if ("admin-token".equals(token)){
                Example example = new Example(UserRole.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("userId",1);
                UserRole userRole =  userRoleMapper.selectOneByExample(example);

                Example example1 = new Example(Role.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("id",userRole.getRoleId());
                Role role =  roleMapper.selectOneByExample(example1);

                if (role == null){
                    return null;
                }

                UserDTO userDTO = new UserDTO();
                List<String> list = new ArrayList<>();
                list.add(role.getType());
                userDTO.setRoles(list);
                userDTO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                userDTO.setName(role.getName());
                userDTO.setIntroduction(role.getIntroduction());

                return userDTO;
            }
        }
        return null;
    }
}

