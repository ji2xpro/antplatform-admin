package com.antplatform.admin.web.action.monitor;

import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.monitor.ServerBiz;
import com.antplatform.admin.web.entity.monitor.vo.ServerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:39:31
 * @description:
 */
@RestController
@RequestMapping("/server")
@Api(value = "ServerAction|服务监控的前端控制器")
public class ServerAction {

    @Autowired
    private ServerBiz serverBiz;

    @GetMapping(value = "/monitor")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询服务监控数据", notes = "展示服务监控数据信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult<ServerVO> queryServerInfo() {
        Response<ServerDTO> response = serverBiz.queryServerInfo();

        if (response.isSuccess()){
            ServerDTO serverDTO = response.getData();

            ServerVO serverVO = TransformUtils.simpleTransform(serverDTO, ServerVO.class);

            return AjaxResult.createSuccessResult(serverVO);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
}
