package com.antplatform.admin.service;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.AuthorityService;
import com.antplatform.admin.biz.service.MenuService;
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
import com.antplatform.admin.service.port.mapper.MenuMapper;
import com.antplatform.admin.service.port.mapper.PermissionMapper;
import com.antplatform.admin.service.port.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

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
    private AuthorityService authorityService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

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
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleIds(ids);

        // 根据角色查询权限（旧、准备废弃）
        Collection<Permission> permissions = permissionService.findBySpec(roleSpec);
        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);
        List<PermissionDTO> resources = UtilHandler.assembleResourceTree(permissionDTOS);

        // 根据角色查询权限
        Collection<Authority> authorities = authorityService.findBySpec(roleSpec);
        Set<String> authCode = authorities.stream().map(Authority::getCode).collect(Collectors.toSet());

        // 根据角色查询菜单
        Set<Integer> authSet = authorities.stream().map(e -> e.getId()).collect(Collectors.toSet());
        Collection<Menu> menus = menuService.findBySpec();
        menus =  menus.stream().filter(e->authSet.contains(e.getAuthorityId())).collect(Collectors.toList());
        Collection<MenuDTO> menuDTOS = menuMapper.toDto(menus);
        List<MenuDTO> menuDTOList = UtilHandler.assembleResourceTree(menuDTOS);

        // 组装数据
        UserDTO userDTO = userMapper.toDto(user);
        userDTO.setRoles(roles);
        userDTO.setAuth(authCode);
        userDTO.setResources(resources);
        userDTO.setMenus(menuDTOList);

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
}
