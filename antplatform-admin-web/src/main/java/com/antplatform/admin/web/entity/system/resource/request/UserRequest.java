package com.antplatform.admin.web.entity.system.resource.request;

import com.antplatform.admin.common.enums.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: maoyan
 * @date: 2020/8/31 16:37:48
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "UserRequest对象", description = "角色查询信息")
public class UserRequest {
    @ApiModelProperty(value = "组织Id", dataType = DataType.INTEGER)
    private Integer organizationId;

    @ApiModelProperty(value = "账号", dataType = DataType.STRING)
    private String username;

    @ApiModelProperty(value = "手机号", dataType = DataType.STRING)
    private String mobile;

    @ApiModelProperty(value = "邮箱", dataType = DataType.STRING)
    private String email;

    @ApiModelProperty(value = "角色", dataType = DataType.INTEGER)
    private Integer roleId;

    @ApiModelProperty(value = "状态", dataType = DataType.INTEGER)
    private Integer status;

    @ApiModelProperty(value = "页码", required = true, dataType = DataType.INTEGER)
    @NotNull(message="页码数不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页数量", required = true, dataType = DataType.INTEGER)
    @NotNull(message="每页数量不能为空")
    private Integer pageSize;
}
