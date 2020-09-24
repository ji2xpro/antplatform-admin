package com.antplatform.admin.service;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.PermissionMapper;
import com.antplatform.admin.service.port.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PermissionMapper permissionMapper;

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
        // 查询用户
        User user = userService.findBySpec(userSpec);
        if (StringUtils.isEmpty(user)){
            return Responses.fail(ResponseCode.INVALID_USER);
        }
        // 根据用户查询角色
        Collection<Role> userRoles = roleService.findBySpec(userSpec);
        if (CollectionUtils.isEmpty(userRoles)){
            return Responses.fail(ResponseCode.INVALID_ROLE);
        }

        Set<String> roles = new HashSet<>();
        Collection<Integer> ids = new ArrayList<>();
        for (Role role:userRoles) {
            roles.add(role.getKeypoint());
            ids.add(role.getId());
        }
        // 根据角色查询权限
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleIds(ids);
        Collection<Permission> permissions = permissionService.findBySpec(roleSpec);

//        PermissionListSpec permissionListSpec = new PermissionListSpec();
//        permissionListSpec.setIds(ids);
//        Collection<Permission> permissions = permissionService.findBySpec(permissionListSpec);
        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);

        UserDTO userDTO = userMapper.toDto(user);
        userDTO.setRoles(roles);


        List<PermissionDTO> menus = this.assembleResourceTree(permissionDTOS);

        userDTO.setResources(menus);

        return Responses.of(userDTO);
    }

    /**
     * 组装子父级目录
     * @param resourceList
     * @return
     */
    private List<PermissionDTO> assembleResourceTree(Collection<PermissionDTO> resourceList) {
        Map<Integer, PermissionDTO> resourceMap = new HashMap<>();
        List<PermissionDTO> menus = new ArrayList<>();
        for (PermissionDTO resource : resourceList) {
            resourceMap.put(resource.getId(), resource);
        }
        for (PermissionDTO resource : resourceList) {
            Integer treePId = resource.getParentId();
            PermissionDTO resourceTree = resourceMap.get(treePId);
            if (null != resourceTree && !resource.equals(resourceTree)) {
                List<PermissionDTO> nodes = resourceTree.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<>();
                    resourceTree.setChildren(nodes);
                }
                nodes.add(resource);
            } else {
                menus.add(resource);
            }
        }
        return menus;
    }
}
