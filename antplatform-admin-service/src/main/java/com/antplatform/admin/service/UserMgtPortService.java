package com.antplatform.admin.service;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.model.UserRole;
import com.antplatform.admin.biz.service.UserRoleService;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.base.component.JwtComponent;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: maoyan
 * @date: 2020/7/9 13:17:23
 * @description:
 */
@Service("UserMgtApi")
public class UserMgtPortService implements UserMgtApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询指定用户
     *
     * @param userMgtSpec
     * @return
     */
    @Override
    public Response<UserDTO> queryUser(UserMgtSpec userMgtSpec) {
        User user = userService.getUser(userMgtSpec);
        if (user == null){
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(),"用户名或密码输入错误");
        }

        UserDTO userDTO = userMapper.toDto(user);
        userDTO.setToken("admin-token");

        return Responses.of(userDTO);
    }

    /**
     * 查询用户信息
     *
     * @param userMgtSpec
     * @return
     */
    @Override
    public Response<UserDTO> queryUserRole(UserMgtSpec userMgtSpec) {
        UserDTO userDTO = userService.getUserRole(userMgtSpec);

        if (userDTO == null){
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(),"");
        }
        return Responses.of(userDTO);
    }

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public Response<UserDTO> queryUserInfo(Integer id) {
        UserSpec userSpec = new UserSpec();
        userSpec.setUserId(id);
        User user = userService.findBySpec(userSpec);
        if (StringUtils.isEmpty(user)){
            return Responses.fail(ResponseCode.INVALID_USER);
        }
        Collection<Role> userRoles = userRoleService.findBySpec(userSpec);

        if (CollectionUtils.isEmpty(userRoles)){
            return Responses.fail(ResponseCode.INVALID_ROLE);
        }
        Set<String> roles = new HashSet<>();
        for (Role role:userRoles) {
            roles.add(role.getKeypoint());
        }
        UserDTO userDTO = userMapper.toDto(user);
        userDTO.setRoles(roles);

        return Responses.of(userDTO);
    }
}
