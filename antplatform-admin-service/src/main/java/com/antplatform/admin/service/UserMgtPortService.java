package com.antplatform.admin.service;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.dto.PageModel;
import com.antplatform.admin.common.dto.PageModelHelper;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.PermissionMapper;
import com.antplatform.admin.service.port.mapper.UserMapper;
import com.google.common.collect.Lists;
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

//        List<PermissionDTO> menus = this.assembleResourceTree(permissionDTOS);

//        List<PermissionDTO> menus = Lists.newArrayList(portService.getPermissionTree(permissions,true));

        List<PermissionDTO> menus = UtilHandler.assembleResourceTree(permissionDTOS);

        userDTO.setResources(menus);

        return Responses.of(userDTO);
    }

    /**
     * 分页查询用户列表
     *
     * @param spec
     * @return
     */
    @Override
    public PagedResponse<UserDTO> findPageBySpec(UserPageSpec spec) {
        PageModel pageModel = userService.findPageBySpec(spec);

        List<UserDTO> userDTOList = (List<UserDTO>) userMapper.toDto((List<User>) pageModel.getRecords());
        for (UserDTO userDTO: userDTOList) {
            UserSpec userSpec = new UserSpec();
            userSpec.setUserId(userDTO.getId());
            // 根据用户查询角色
            Collection<Role> userRoles = roleService.findBySpec(userSpec);
            List<Role> roles = new ArrayList<>(userRoles);
            userDTO.setRoleName(roles.get(0).getName());
        }

        return PagedResponses.of(userDTOList,
                pageModel.getRecordCount(), PageModelHelper.hasNext(pageModel));
    }

    /**
     * 组装子父级目录
     * @param resourceList
     * @return
     */
//    private List<PermissionDTO> assembleResourceTree(Collection<PermissionDTO> resourceList) {
//        Map<Integer, PermissionDTO> resourceMap = new HashMap<>();
//        List<PermissionDTO> menus = new ArrayList<>();
//        for (PermissionDTO resource : resourceList) {
//            resourceMap.put(resource.getId(), resource);
//        }
//        for (PermissionDTO resource : resourceList) {
//            Integer treePId = resource.getParentId();
//            PermissionDTO resourceTree = resourceMap.get(treePId);
//            if (null != resourceTree && !resource.equals(resourceTree)) {
//                List<PermissionDTO> nodes = resourceTree.getChildren();
//                if (null == nodes) {
//                    nodes = new ArrayList<>();
//                    resourceTree.setChildren(nodes);
//                }
//                nodes.add(resource);
//            } else {
//                menus.add(resource);
//            }
//        }
//        return menus;
//    }
}
