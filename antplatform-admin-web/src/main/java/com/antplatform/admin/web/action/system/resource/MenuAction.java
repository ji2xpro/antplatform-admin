package com.antplatform.admin.web.action.system.resource;

import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.resource.MenuBiz;
import com.antplatform.admin.web.entity.system.resource.request.MenuRequest;
import com.antplatform.admin.web.entity.system.resource.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:39:31
 * @description:
 */
@RestController
@RequestMapping("/menu")
@Api(value = "MenuAction|菜单相关的前端控制器")
public class MenuAction {

    @Autowired
    private MenuBiz menuBiz;

    @GetMapping(value = "/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询菜单资源树", notes = "树状展示菜单资源信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult<List<MenuVO>> queryMenus() {
        Response<List<MenuDTO>> response = menuBiz.queryMenus();

        if (response.isSuccess()){
            List<MenuDTO> menuDTOS = response.getData();

            List<MenuVO> menuVOS = TransformUtils.simpleTransform(menuDTOS,MenuVO.class);

            return AjaxResult.createSuccessResult(menuVOS);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    @PostMapping("/save")
    @RequiresRoles("admin")
    @ApiOperation(value = "保存菜单")
    public AjaxResult<Boolean> save(@RequestBody MenuRequest menuRequest) {
        MenuSpec menuSpec = new MenuSpec();
        menuSpec.setId(menuRequest.getId());
        menuSpec.setName(menuRequest.getName());
        menuSpec.setParentId(menuRequest.getParentId());
        menuSpec.setAuthorityId(menuRequest.getAuthorityId());
        menuSpec.setIcon(menuRequest.getIcon());
        menuSpec.setPath(menuRequest.getPath());
        menuSpec.setUrl(menuRequest.getUrl());
        menuSpec.setSort(menuRequest.getSort());
        menuSpec.setDescription(menuRequest.getDescription());
        menuSpec.setStatus(menuRequest.getStatus());

        Response<Boolean> response = menuBiz.save(menuSpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }

    /**
     * 删除角色
     */
    @RequiresRoles("admin")
    @PostMapping("/delete/{menuId}")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(paramType = "path", name = "menuId", value = "菜单ID", required = true, dataType = "Integer")
    public AjaxResult<Boolean> delete(@PathVariable("menuId") Integer menuId) {
        if (null == menuId) {
            return AjaxResult.createFailedResult(1000, "ID不能为空");
        }
        MenuSpec menuSpec = new MenuSpec();
        menuSpec.setId(menuId);
        Response<Boolean> response = menuBiz.delete(menuSpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(true);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
}
