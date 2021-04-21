package com.antplatform.admin.web.biz.system.resource.impl;

import com.antplatform.admin.api.RoleMgtApi;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.RoleAuthSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.biz.system.resource.RoleBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:34:36
 * @description:
 */
@Slf4j
@Service("RoleBiz")
public class RoleBizImpl implements RoleBiz {

    @Autowired
    private RoleMgtApi roleMgtApi;

    /**
     * 分页查询角色列表
     *
     * @param rolePageSpec
     * @return
     */
    @Override
    public PagedResponse<RoleDTO> queryRolePage(RolePageSpec rolePageSpec) {
        try {
            return roleMgtApi.findPageBySpec(rolePageSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.findPageBySpec exception, spec = %s", rolePageSpec), e);
            return PagedResponses.requestTimeout();
        }
    }

    /**
     * 查询角色信息
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Response<RoleDTO> queryRole(RoleSpec roleSpec) {
        try {
            return roleMgtApi.findBySpec(roleSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.findBySpec exception, spec = %s", roleSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 保存角色信息
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Response<Boolean> save(RoleSpec roleSpec) {
        try {
            return roleMgtApi.save(roleSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.save exception, spec = %s", roleSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 保存角色权限
     *
     * @param rolePermissionSpec
     * @return
     */
    @Override
    public Response<Boolean> saveRolePermission(RolePermissionSpec rolePermissionSpec) {
        try {
            return roleMgtApi.assignPermission(rolePermissionSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.assignPermission exception, spec = %s", rolePermissionSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 保存角色权限
     *
     * @param roleAuthSpec
     * @return
     */
    @Override
    public Response<Boolean> saveRoleAuth(RoleAuthSpec roleAuthSpec) {
        try {
            return roleMgtApi.assignAuth(roleAuthSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.assignAuth exception, spec = %s", roleAuthSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 删除角色信息
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Response<Boolean> delete(RoleSpec roleSpec) {
        try {
            return roleMgtApi.delete(roleSpec);
        } catch (Exception e) {
            log.error(String.format("invoke roleMgtApi.delete exception, spec = %s", roleSpec), e);
            return Responses.requestTimeout();
        }
    }


}
