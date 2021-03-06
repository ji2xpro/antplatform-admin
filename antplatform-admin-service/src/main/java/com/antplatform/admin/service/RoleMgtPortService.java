package com.antplatform.admin.service;

import com.antplatform.admin.api.RoleMgtApi;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleAuthDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.RoleAuthSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.common.dto.PageModel;
import com.antplatform.admin.common.dto.PageModelHelper;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.PermissionMapper;
import com.antplatform.admin.service.port.mapper.RoleAuthMapper;
import com.antplatform.admin.service.port.mapper.RoleMapper;
import com.antplatform.admin.service.port.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:47:44
 * @description:
 */
@Service("RoleMgtApi")
public class RoleMgtPortService implements RoleMgtApi {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Autowired
    private RoleAuthMapper roleAuthMapper;

    /**
     * 分页查询角色列表
     *
     * @param spec
     * @return
     */
    @Override
    public PagedResponse<RoleDTO> findPageBySpec(RolePageSpec spec) {
        PageModel pageModel = roleService.findPageBySpec(spec);

        List<RoleDTO> roleDTOList = (List<RoleDTO>) roleMapper.toDto((List<Role>) pageModel.getRecords());
        return PagedResponses.of(roleDTOList,
                pageModel.getRecordCount(), PageModelHelper.hasNext(pageModel));
    }

    /**
     * 查询角色信息
     *
     * @param spec
     * @return
     */
    @Override
    public Response<RoleDTO> findBySpec(RoleSpec spec) {
        Role role = roleService.findBySpec(spec);

        if (role == null) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "查询用户信息失败");
        }
        RoleDTO roleDTO = roleMapper.toDto(role);

        return Responses.of(roleDTO);
    }

    /**
     * 保存角色信息
     *
     * @param spec
     * @return
     */
    @Override
    public Response<Boolean> save(RoleSpec spec) {
        Role role = roleMapper.commandToEntity(spec);

        Boolean temp = roleService.save(role);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "保存角色信息失败");
        }
        return Responses.of(temp);
    }

    /**
     * 角色分配权限
     *
     * @param spec
     * @return
     */
    @Override
    public Response<Boolean> assignPermission(RolePermissionSpec spec) {
        Collection<RolePermission> rolePermissions = new ArrayList<>();

        List<RolePermissionDTO> addList = spec.getAddResources();
        if (!CollectionUtils.isEmpty(addList)) {
            Collection<RolePermission> adds = rolePermissionMapper.commandToEntity(spec.getAddResources());
            adds.forEach(add -> add.setIsDelete(IsDeleteStatus.EXITS.getCode()));

            rolePermissions.addAll(adds);
        }

        List<RolePermissionDTO> delList = spec.getDelResources();
        if (!CollectionUtils.isEmpty(delList)) {
            Collection<RolePermission> deletes = rolePermissionMapper.commandToEntity(spec.getDelResources());
            deletes.forEach(delete -> delete.setIsDelete(IsDeleteStatus.DELETED.getCode()));

            rolePermissions.addAll(deletes);
        }
        return Responses.of(roleService.assignPermission(rolePermissions));
    }

    /**
     * 角色分配权限
     *
     * @param spec
     * @return
     */
    @Override
    public Response<Boolean> assignAuth(RoleAuthSpec spec) {
        Collection<RoleAuthority> roleAuthorities = new ArrayList<>();

        List<RoleAuthDTO> addList = spec.getAddAuths();
        if (!CollectionUtils.isEmpty(addList)) {
            Collection<RoleAuthority> adds = roleAuthMapper.commandToEntity(addList);
            adds.forEach(add -> add.setIsDelete(IsDeleteStatus.EXITS.getCode()));

            roleAuthorities.addAll(adds);
        }

        List<RoleAuthDTO> delList = spec.getDelAuths();
        if (!CollectionUtils.isEmpty(delList)) {
            Collection<RoleAuthority> deletes = roleAuthMapper.commandToEntity(delList);
            deletes.forEach(delete -> delete.setIsDelete(IsDeleteStatus.DELETED.getCode()));

            roleAuthorities.addAll(deletes);
        }
        return Responses.of(roleService.assignAuth(roleAuthorities));
    }

    /**
     * 删除角色信息
     *
     * @param spec
     * @return
     */
    @Override
    public Response<Boolean> delete(RoleSpec spec) {
        Role role = roleMapper.commandToEntity(spec);
        role.setIsDelete(IsDeleteStatus.DELETED.getCode());

        Boolean temp = roleService.delete(role);

        return Responses.of(temp);
    }


}
