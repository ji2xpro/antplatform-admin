package com.antplatform.admin.core.service;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtRequest;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.core.mapper.RoleMapper;
import com.antplatform.admin.core.mapper.UserMapper;
import com.antplatform.admin.core.mapper.UserRoleMapper;
import com.antplatform.admin.core.model.Login;
import com.antplatform.admin.core.model.Role;
import com.antplatform.admin.core.model.User;
import com.antplatform.admin.core.model.UserRole;
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
//    @Autowired
//    UserDAO userDAO;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;



//    @Override
//    public boolean isExist(String username) {
//        User user = getByName(username);
//        return null!=user;
//    }

//    @Override
//    public User getByName(String username) {
//        return userDAO.findByUsername(username);
//    }

//    @Override
//    public User get(String username, String password){
//        return userDAO.getByUsernameAndPassword(username, password);
//    }

//    @Override
//    public void add(User user) {
//        userDAO.save(user);
//    }

    @Override
    public UserDTO getByUsernameAndPassword(UserMgtRequest userMgtRequest) {
        String username = userMgtRequest.getUsername();
        String password = userMgtRequest.getPassword();

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        User user =  userMapper.selectOneByExample(example);

        UserDTO userDTO = new UserDTO();
        if (user == null){
            return null;
        }
        userDTO.setToken("admin-token");
        return userDTO;
    }

    @Override
    public UserDTO getUserRole(UserMgtRequest userMgtRequest) {
        String token = userMgtRequest.getToken();
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
        return null;
    }
}

