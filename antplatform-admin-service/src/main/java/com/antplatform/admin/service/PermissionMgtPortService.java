package com.antplatform.admin.service;

import com.antplatform.admin.api.PermissionMgtApi;
import com.antplatform.admin.api.RoleMgtApi;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.common.dto.PageModel;
import com.antplatform.admin.common.dto.PageModelHelper;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.PermissionMapper;
import com.antplatform.admin.service.port.mapper.RoleMapper;
import com.antplatform.admin.service.port.mapper.RolePermissionMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:47:44
 * @description:
 */
@Service("PermissionMgtApi")
public class PermissionMgtPortService implements PermissionMgtApi {

    @Autowired
    private PermissionService permissionService;


    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查询权限列表
     *
     * @param spec
     * @return
     */
    @Override
    public Response<List<PermissionDTO>> findBySpec(PermissionSpec spec) {

        Collection<Permission> permissions = permissionService.findBySpec(spec);

//        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);
//
//        List<PermissionDTO> menus = this.assembleResourceTree(permissionDTOS);

//        List<PermissionDTO> menus = Lists.newArrayList(portService.getPermissionTree(permissions,true));

        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);

        List<PermissionDTO> menus = UtilHandler.assembleResourceTree(permissionDTOS);

        return Responses.of(menus);
    }

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    @Override
    public Response<Collection<PermissionDTO>> findBySpec(int roleId) {
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(roleId);

        Collection<Permission> permissions = permissionService.findBySpec(roleSpec);

        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);

        return Responses.of(permissionDTOS);
    }
}
