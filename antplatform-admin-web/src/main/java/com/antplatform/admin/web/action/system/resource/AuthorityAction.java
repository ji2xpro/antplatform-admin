package com.antplatform.admin.web.action.system.resource;

import com.antplatform.admin.api.dto.AuthorityDTO;
import com.antplatform.admin.api.request.AuthoritySpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.resource.AuthorityBiz;
import com.antplatform.admin.web.entity.system.resource.request.AuthorityRequest;
import com.antplatform.admin.web.entity.system.resource.vo.AuthorityVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:39:31
 * @description:
 */
@RestController
@RequestMapping("/authority")
@Validated
@Api(value = "AuthorityAction|权限相关的前端控制器")
public class AuthorityAction {

    @Autowired
    private AuthorityBiz authorityBiz;

    @GetMapping(value = "/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询权限资源树", notes = "树状展示权限资源信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult<List<AuthorityVO>> queryAuthorities() {
        Response<List<AuthorityDTO>> response = authorityBiz.queryAuthorities();

        if (response.isSuccess()){
            List<AuthorityDTO> authorityDTOS = response.getData();

            List<AuthorityVO> authorityVOS = TransformUtils.simpleTransform(authorityDTOS, AuthorityVO.class);

            return AjaxResult.createSuccessResult(authorityVOS);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    @PostMapping("/save")
    @RequiresRoles("admin")
    @ApiOperation(value = "保存权限")
    public AjaxResult<Boolean> save(@RequestBody AuthorityRequest authorityRequest) {
        AuthoritySpec authoritySpec = new AuthoritySpec();
        authoritySpec.setId(authorityRequest.getId());
        authoritySpec.setName(authorityRequest.getName());
        authoritySpec.setParentId(authorityRequest.getParentId());
        authoritySpec.setCode(authorityRequest.getCode());
        authoritySpec.setPath(authorityRequest.getPath());
        authoritySpec.setType(authorityRequest.getType());
        authoritySpec.setSort(authorityRequest.getSort());
        authoritySpec.setDescription(authorityRequest.getDescription());
        authoritySpec.setStatus(authorityRequest.getStatus());

        Response<Boolean> response = authorityBiz.save(authoritySpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    /**
     * 删除角色
     */
    @RequiresRoles("admin")
    @PostMapping("/delete/{authId}")
    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(paramType = "path", name = "authId", value = "权限ID", required = true, dataType = "Integer")
    public AjaxResult<Boolean> delete(@PathVariable("authId") Integer authId) {
        if (null == authId) {
            return AjaxResult.createFailedResult(1000, "ID不能为空");
        }
        AuthoritySpec authoritySpec = new AuthoritySpec();
        authoritySpec.setId(authId);
        Response<Boolean> response = authorityBiz.delete(authoritySpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    /**
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/role/{roleId}")
    @RequiresRoles("admin")
    @ApiOperation(value = "获取角色的权限资源")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Integer")
    public AjaxResult<List<AuthorityVO>> queryRoleResource(@NotNull(message="角色id不能为空") @PathVariable("roleId") Integer roleId) {

        Response<Collection<AuthorityDTO>> response = authorityBiz.queryRoleAuth(roleId);

        if (response.isSuccess()){
            List<AuthorityDTO> authorityDTOS = Lists.newArrayList(response.getData());

            List<AuthorityVO> authorityVOS = TransformUtils.simpleTransform(authorityDTOS, AuthorityVO.class);

            return AjaxResult.createSuccessResult(authorityVOS);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
}
