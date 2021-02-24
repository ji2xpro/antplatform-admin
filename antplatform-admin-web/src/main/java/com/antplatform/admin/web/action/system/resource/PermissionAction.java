package com.antplatform.admin.web.action.system.resource;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.resource.PermissionBiz;
import com.antplatform.admin.web.entity.system.resource.PermissionVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:39:31
 * @description:
 */
@RestController
@RequestMapping("/resource")
@Api(value = "PermissionAction|权限资源相关的前端控制器")
public class PermissionAction {

    @Autowired
    private PermissionBiz permissionBiz;

    @GetMapping(value = "/tree")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询权限资源树", notes = "树状展示权限资源信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult<List<PermissionVO>> queryResourceTree(Integer parentId) {
        PermissionSpec permissionSpec = new PermissionSpec();
        permissionSpec.setParentId(parentId);

        Response<List<PermissionDTO>> response = permissionBiz.queryPermissions(permissionSpec);

        if (response.isSuccess()){
            List<PermissionDTO> permissionDTOS = response.getData();

            List<PermissionVO> permissionVOS = TransformUtils.simpleTransform(permissionDTOS,PermissionVO.class);

            return AjaxResult.createSuccessResult(permissionVOS);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    /**
     * 获取角色资源
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/role/{roleId}")
    @RequiresRoles("admin")
    @ApiOperation(value = "获取角色的权限资源")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Integer")
    public AjaxResult<List<PermissionVO>> queryRoleResource(@PathVariable("roleId") Integer roleId) {

        Response<Collection<PermissionDTO>> response = permissionBiz.queryRolePermission(roleId);

        if (response.isSuccess()){
            List<PermissionDTO> permissionDTOS = Lists.newArrayList(response.getData());

            List<PermissionVO> permissionVOS = TransformUtils.simpleTransform(permissionDTOS,PermissionVO.class);

            return AjaxResult.createSuccessResult(permissionVOS);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
//    public AjaxResult<List<RolePermissionVO>> queryRoleResource(@PathVariable("roleId") Integer roleId) {
//
//        Response<Collection<RolePermissionDTO>> response = permissionBiz.queryRolePermission1(roleId);
//
//        if (response.isSuccess()){
//            List<RolePermissionDTO> rolePermissionDTOS = Lists.newArrayList(response.getData());
//
//            List<RolePermissionVO> rolePermissionVOS = TransformUtils.simpleTransform(rolePermissionDTOS,RolePermissionVO.class);
//
//            return AjaxResult.createSuccessResult(rolePermissionVOS);
//        }
//        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
//    }

}
