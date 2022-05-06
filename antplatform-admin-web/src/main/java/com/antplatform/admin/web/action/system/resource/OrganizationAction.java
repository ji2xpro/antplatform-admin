package com.antplatform.admin.web.action.system.resource;

import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.result.Paging;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.resource.OrganizationBiz;
import com.antplatform.admin.web.entity.system.resource.request.OrganizationRequest;
import com.antplatform.admin.web.entity.system.resource.vo.OrganizationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Role前端控制器
 *
 * @author: maoyan
 * @date: 2020/8/31 15:52:55
 * @description:
 */
@RestController
@RequestMapping("/organization")
@Validated
@Api(value = "OrganizationAction|组织相关的前端控制器")
public class OrganizationAction {

    @Autowired
    private OrganizationBiz organizationBiz;

    @GetMapping("/list")
    @RequiresAuthentication
    @ApiOperation(value = "查询组织列表")
    public AjaxResult<List<OrganizationVO>> list(OrganizationRequest organizationRequest) {
        OrganizationPageSpec organizationPageSpec = new OrganizationPageSpec();
        organizationPageSpec.setName(organizationRequest.getName());
        organizationPageSpec.setKeypoint(organizationRequest.getKeypoint());
        organizationPageSpec.setPageNo(organizationRequest.getPageNo());
        organizationPageSpec.setPageSize(organizationRequest.getPageSize());

        PagedResponse<OrganizationDTO> response = organizationBiz.queryOrganizationPage(organizationPageSpec);

        if (response.isSuccess()) {
            List<OrganizationDTO> organizationDTOS = response.getData();

            List<OrganizationVO> organizationVOS = TransformUtils.simpleTransform(organizationDTOS, OrganizationVO.class);

            return AjaxResult.createSuccessResult(organizationVOS, new Paging(organizationRequest.getPageNo(), organizationRequest.getPageSize(), response.getTotalHits(), response.isHasNext()));
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    @PostMapping(value = "/name/check")
    @RequiresRoles("admin")
    @ApiOperation(value = "校验组织名称是否存在", notes = "校验组织名称是否存在")
    public AjaxResult<Boolean> checkOrganizationName(@RequestParam(value = "id", required = false) Integer id,
                                                     @RequestParam(value = "organizationName") String organizationName) {
        OrganizationSpec organizationSpec = new OrganizationSpec();
        organizationSpec.setOrganizationId(id);
        organizationSpec.setName(organizationName);

        Response<OrganizationDTO> response = organizationBiz.queryOrganization(organizationSpec);

        if (!response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

    @PostMapping(value = "/key/check")
    @RequiresRoles("admin")
    @ApiOperation(value = "校验组织标识是否存在", notes = "校验组织标识是否存在")
    public AjaxResult<Boolean> checkOrganizationKey(@RequestParam(value = "id", required = false) Integer id,
                                                    @NotEmpty(message = "组织标识不能为空")
                                                    @RequestParam(value = "organizationKey") String organizationKey) {
        OrganizationSpec organizationSpec = new OrganizationSpec();
        organizationSpec.setOrganizationId(id);
        organizationSpec.setKeypoint(organizationKey);

        Response<OrganizationDTO> response = organizationBiz.queryOrganization(organizationSpec);

        if (!response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }

//    /**
//     * 添加角色
//     */
//    @PostMapping("/create")
//    @RequiresRoles("admin")
//    @ApiOperation(value = "添加角色")
////    @AroundLog(name = "添加角色")
//    public AjaxResult<Boolean> create(@RequestBody RoleRequest roleRequest) {
//        RoleSpec roleSpec = new RoleSpec();
//        roleSpec.setName(roleRequest.getName());
//        roleSpec.setKeypoint(roleRequest.getKeypoint());
//        roleSpec.setType(roleRequest.getType());
//        roleSpec.setStatus(roleRequest.getStatus());
//        roleSpec.setRemark(roleRequest.getRemark());
//
//        Response<Boolean> response = roleBiz.saveRole(roleSpec);
//
//        if (response.isSuccess()) {
//            return AjaxResult.createSuccessResult(true);
//        }
//        return AjaxResult.createSuccessResult(false);
//    }



    @PostMapping("/update")
    @RequiresRoles("admin")
    @ApiOperation(value = "更新组织机构")
//    @AroundLog(name = "更新组织机构")
    public AjaxResult<Boolean> update(@RequestBody OrganizationRequest organizationRequest) {
        OrganizationSpec organizationSpec = new OrganizationSpec();
        organizationSpec.setOrganizationId(organizationRequest.getId());
        organizationSpec.setName(organizationRequest.getName());
        organizationSpec.setKeypoint(organizationRequest.getKeypoint());
        organizationSpec.setType(organizationRequest.getType());
        organizationSpec.setIcon(organizationRequest.getIcon());
        organizationSpec.setLevel(organizationRequest.getLevel());
        organizationSpec.setProvince(organizationRequest.getProvince());
        organizationSpec.setCity(organizationRequest.getCity());
        organizationSpec.setArea(organizationRequest.getArea());
        organizationSpec.setStreet(organizationRequest.getStreet());
        organizationSpec.setDescription(organizationRequest.getDescription());

        Response<Boolean> response = organizationBiz.updateOrganization(organizationSpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createSuccessResult(false);
    }


//    /**
//     * 修改角色资源
//     *
//     * @param rolePermissionRequest
//     * @return
//     */
//    @PostMapping(value = "/resource/update")
//    @RequiresRoles("admin")
//    @ApiOperation(value = "修改角色的权限资源")
////    @AroundLog(name = "修改角色的权限资源")
//    public AjaxResult<Boolean> updateRoleResource(@RequestBody RolePermissionRequest rolePermissionRequest) {
//        RolePermissionSpec rolePermissionSpec = new RolePermissionSpec();
//        rolePermissionSpec.setRoleId(rolePermissionRequest.getRoleId());
//        rolePermissionSpec.setAddResources(rolePermissionRequest.getAddResources());
//        rolePermissionSpec.setDelResources(rolePermissionRequest.getDelResources());
//
//        Response<Boolean> response = roleBiz.saveRolePermission(rolePermissionSpec);
//        if (response.isSuccess()) {
//            return AjaxResult.createSuccessResult(true);
//        }
//        return AjaxResult.createSuccessResult(false);
//    }
//
//    @PostMapping("/status/{roleId}/{roleStatus}")
//    @RequiresRoles("admin")
//    @ApiOperation(value = "修改角色状态")
////    @AroundLog(name = "修改角色状态")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "roleStatus", value = "角色状态", required = true, dataType = "String", paramType = "path")})
//    public AjaxResult<Boolean> updateStatus(@PathVariable("roleId") Integer roleId,
//                                            @PathVariable("roleStatus") Integer roleStatus) {
//        if (null == roleId || StringUtils.isEmpty(roleStatus)) {
//            return AjaxResult.createFailedResult(1000, "ID和状态不能为空");
//        }
//
//        RoleSpec roleSpec = new RoleSpec();
//        roleSpec.setRoleId(roleId);
//        roleSpec.setStatus(roleStatus);
//
//        Response<Boolean> response = roleBiz.saveRole(roleSpec);
//
//        if (response.isSuccess()) {
//            return AjaxResult.createSuccessResult(true);
//        }
//        return AjaxResult.createSuccessResult(false);
//    }
//
//    /**
//     * 删除角色
//     */
//    @RequiresRoles("admin")
//    @PostMapping("/delete/{roleId}")
//    @ApiOperation(value = "删除角色")
////    @AroundLog(name = "删除角色")
//    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Integer")
//    public AjaxResult<Boolean> delete(@PathVariable("roleId") Integer roleId) {
//        if (null == roleId) {
//            return AjaxResult.createFailedResult(1000, "ID不能为空");
//        }
//        RoleSpec roleSpec = new RoleSpec();
//        roleSpec.setRoleId(roleId);
//        Response<Boolean> response = roleBiz.delete(roleSpec);
//
//        if (response.isSuccess()) {
//            return AjaxResult.createSuccessResult(true);
//        }
//        return AjaxResult.createSuccessResult(false);
//    }


}
