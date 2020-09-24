package com.antplatform.admin.web.action.system.permission;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.result.Paging;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.permission.PermissionBiz;
import com.antplatform.admin.web.biz.system.permission.RoleBiz;
import com.antplatform.admin.web.entity.system.permission.PermissionVO;
import com.antplatform.admin.web.entity.system.permission.RolePermissionRequest;
import com.antplatform.admin.web.entity.system.permission.RolePermissionVO;
import com.antplatform.admin.web.entity.system.permission.RoleRequest;
import com.antplatform.admin.web.entity.system.permission.RoleVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Role前端控制器
 *
 * @author: maoyan
 * @date: 2020/8/31 15:52:55
 * @description:
 */
@RestController
@RequestMapping("/role")
@Api(value = "RoleController|角色相关的前端控制器")
public class RoleAction {

    @Autowired
    private RoleBiz roleBiz;

    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询角色列表")
    public AjaxResult<List<RoleVO>> list(RoleRequest roleRequest) {
        RolePageSpec rolePageSpec = new RolePageSpec();
        rolePageSpec.setId(roleRequest.getId());
        rolePageSpec.setName(roleRequest.getName());
        rolePageSpec.setKeypoint(roleRequest.getKeypoint());
        rolePageSpec.setStatus(roleRequest.getStatus());
        rolePageSpec.setPageNo(roleRequest.getPageNo());
        rolePageSpec.setPageSize(roleRequest.getPageSize());

        PagedResponse<RoleDTO> response = roleBiz.queryRoles(rolePageSpec);

        if (response.isSuccess()) {
            List<RoleDTO> roleDTOS = response.getData();

            List<RoleVO> roleVOS = TransformUtils.simpleTransform(roleDTOS,RoleVO.class);

            return AjaxResult.createSuccessResult(roleVOS, new Paging(roleRequest.getPageNo(), roleRequest.getPageSize(), response.getTotalHits(), response.isHasNext()));
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    @PostMapping(value = "/name/check")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "校验角色名称是否存在", notes = "校验角色名称是否存在")
    public AjaxResult<Boolean> checkRoleName(@RequestParam(value = "id",required = false) Integer id,
                                             @RequestParam(value = "roleName") String roleName){
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(id);
        roleSpec.setName(roleName);

        Response<RoleDTO> response = roleBiz.queryRole(roleSpec);

        if (!response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    @PostMapping(value = "/key/check")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "校验角色标识是否存在", notes = "校验角色标识是否存在")
    public AjaxResult<Boolean> checkRoleKey(@RequestParam(value = "id",required = false) Integer id,
                                            @RequestParam(value = "roleKey") String roleKey) {
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(id);
        roleSpec.setKeypoint(roleKey);

        Response<RoleDTO> response = roleBiz.queryRole(roleSpec);

        if (!response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    /**
     * 添加角色
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加角色")
//    @AroundLog(name = "添加角色")
    public AjaxResult<Boolean> create(@RequestBody RoleRequest roleRequest) {
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setName(roleRequest.getName());
        roleSpec.setKeypoint(roleRequest.getKeypoint());
        roleSpec.setType(roleRequest.getType());
        roleSpec.setStatus(roleRequest.getStatus());
        roleSpec.setRemark(roleRequest.getRemark());

        Response<Boolean> response = roleBiz.saveRole(roleSpec);

        if (response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }


    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新角色")
//    @AroundLog(name = "更新角色")
    public AjaxResult<Boolean> saveOrUpdate(@RequestBody RoleRequest roleRequest) {

        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(roleRequest.getId());
        roleSpec.setName(roleRequest.getName());
        roleSpec.setKeypoint(roleRequest.getKeypoint());
        roleSpec.setRemark(roleRequest.getRemark());
        roleSpec.setStatus(roleRequest.getStatus());
        roleSpec.setType(roleRequest.getType());

        Response<Boolean> response = roleBiz.saveRole(roleSpec);

        if (response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    /**
     * 修改角色资源
     *
     * @param rolePermissionRequest
     * @return
     */
    @PostMapping(value = "/resource/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "修改角色的权限资源")
//    @AroundLog(name = "修改角色的权限资源")
    public AjaxResult<Boolean> updateRoleResource(@RequestBody RolePermissionRequest rolePermissionRequest) {
        RolePermissionSpec rolePermissionSpec = new RolePermissionSpec();
        rolePermissionSpec.setRoleId(rolePermissionRequest.getRoleId());
        rolePermissionSpec.setAddResources(rolePermissionRequest.getAddResources());
        rolePermissionSpec.setDelResources(rolePermissionRequest.getDelResources());

        Response<Boolean> response = roleBiz.saveRolePermission(rolePermissionSpec);
        if (response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    @PostMapping("/status/{roleId}/{roleStatus}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "修改角色状态")
//    @AroundLog(name = "修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "roleStatus", value = "角色状态", required = true, dataType = "String", paramType = "path") })
    public AjaxResult<Boolean> updateStatus(@PathVariable("roleId") Integer roleId,
                                  @PathVariable("roleStatus") Integer roleStatus) {
        if (null == roleId || StringUtils.isEmpty(roleStatus)) {
            return AjaxResult.createFailedResult(1000,"ID和状态不能为空");
        }

        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(roleId);
        roleSpec.setStatus(roleStatus);

        Response<Boolean> response = roleBiz.saveRole(roleSpec);

        if (response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    /**
     * 删除角色
     */
    @RequiresRoles("SYSADMIN")
    @PostMapping("/delete/{roleId}")
    @ApiOperation(value = "删除角色")
//    @AroundLog(name = "删除角色")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Integer")
    public AjaxResult<Boolean> delete(@PathVariable("roleId") Integer roleId) {
        if (null == roleId) {
            return AjaxResult.createFailedResult(1000,"ID不能为空");
        }
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(roleId);
        Response<Boolean> response = roleBiz.delete(roleSpec);

        if (response.isSuccess()){
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }






}
