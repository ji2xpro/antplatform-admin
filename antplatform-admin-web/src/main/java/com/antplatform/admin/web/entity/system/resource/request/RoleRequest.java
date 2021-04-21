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
@ApiModel(value = "RoleRequest对象", description = "角色查询信息")
public class RoleRequest {
    @ApiModelProperty(value = "序号", dataType = DataType.INTEGER)
    private Integer id;

    @ApiModelProperty(value = "角色名称", dataType = DataType.STRING)
    private String name;

    @ApiModelProperty(value = "角色标识", dataType = DataType.STRING)
    private String keypoint;

    @ApiModelProperty(value = "备注", dataType = DataType.STRING)
    private String remark;

    @ApiModelProperty(value = "类型", dataType = DataType.STRING)
    private Integer type;

    @ApiModelProperty(value = "角色状态", dataType = DataType.INTEGER)
    private Integer status;

    @ApiModelProperty(value = "页码", required = true, dataType = DataType.INTEGER)
    @NotNull(message="页码数不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页数量", required = true, dataType = DataType.INTEGER)
    @NotNull(message="每页数量不能为空")
    private Integer pageSize;
}
