package com.antplatform.admin.web.action.system.resource;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.enums.DataType;
import com.antplatform.admin.common.enums.ParamType;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.result.Paging;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.system.resource.UserBiz;
import com.antplatform.admin.web.entity.system.resource.request.UserRequest;
import com.antplatform.admin.web.entity.system.resource.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 15:51:50
 * @description:
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserAction|用户相关的前端控制器")
public class UserAction {

    @Autowired
    private UserBiz userBiz;

    /**
     * 查询所有用户
     */
    @GetMapping("/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", dataType = DataType.STRING, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "mobile", value = "手机号",dataType = DataType.STRING, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = DataType.STRING, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "roleId", value = "角色", dataType = DataType.INTEGER, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "status", value = "状态", dataType = DataType.STRING, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = DataType.INTEGER, paramType = ParamType.QUERY),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = DataType.INTEGER, paramType = ParamType.QUERY) })
    public AjaxResult<List<UserVO>> list(@ApiIgnore UserRequest userRequest) {
        UserPageSpec userPageSpec = new UserPageSpec();
        userPageSpec.setId(userRequest.getId());
        userPageSpec.setUsername(userRequest.getUsername());
        userPageSpec.setMobile(userRequest.getMobile());
        userPageSpec.setEmail(userRequest.getEmail());
        userPageSpec.setStatus(userRequest.getStatus());
        userPageSpec.setPageNo(userRequest.getPageNo());
        userPageSpec.setPageSize(userRequest.getPageSize());

        PagedResponse<UserDTO> response = userBiz.queryUserPage(userPageSpec);

        if (response.isSuccess()) {
            List<UserDTO> userDTOS = response.getData();

            List<UserVO> userVOS = TransformUtils.simpleTransform(userDTOS, UserVO.class);

            return AjaxResult.createSuccessResult(userVOS, new Paging(userRequest.getPageNo(), userRequest.getPageSize(), response.getTotalHits(), response.isHasNext()));
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
}
