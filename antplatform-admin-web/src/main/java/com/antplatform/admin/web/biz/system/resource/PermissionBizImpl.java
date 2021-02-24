package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.PermissionMgtApi;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:34:36
 * @description:
 */
@Slf4j
@Service("PermissionBiz")
public class PermissionBizImpl implements PermissionBiz{

    @Autowired
    private PermissionMgtApi permissionMgtApi;

    /**
     * 查询权限列表
     *
     * @param permissionSpec
     * @return
     */
    @Override
    public Response<List<PermissionDTO>> queryPermissions(PermissionSpec permissionSpec) {
        try {
            return permissionMgtApi.findBySpec(permissionSpec);
        } catch (Exception e) {
            log.error(String.format("invoke permissionMgtApi.findBySpec exception, spec = %s", permissionSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    @Override
    public Response<Collection<PermissionDTO>> queryRolePermission(int roleId) {
        try {
            return permissionMgtApi.findBySpec(roleId);
        } catch (Exception e) {
            log.error(String.format("invoke permissionMgtApi.findBySpec exception, spec = %s", roleId), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 查询角色权限
     *
     * @param roleId
     * @return
     */
    @Override
    public Response<Collection<RolePermissionDTO>> queryRolePermission1(int roleId) {
        try {
            return permissionMgtApi.findBySpec1(roleId);
        } catch (Exception e) {
            log.error(String.format("invoke permissionMgtApi.findBySpec exception, spec = %s", roleId), e);
            return Responses.requestTimeout();
        }
    }
}
