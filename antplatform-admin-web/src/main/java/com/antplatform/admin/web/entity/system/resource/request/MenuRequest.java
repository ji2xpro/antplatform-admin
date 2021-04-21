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
@ApiModel(value = "MenuRequest对象", description = "菜单查询信息")
public class MenuRequest {
    @ApiModelProperty(value = "序号", dataType = DataType.INTEGER)
    private Integer id;

    @ApiModelProperty(value = "菜单名称", dataType = DataType.STRING)
    private String name;

    @ApiModelProperty(value = "父菜单Id", dataType = DataType.INTEGER)
    private Integer parentId;

    @ApiModelProperty(value = "权限Id", dataType = DataType.INTEGER)
    private Integer authorityId;

    @ApiModelProperty(value = "图标", dataType = DataType.STRING)
    private String icon;

    @ApiModelProperty(value = "路径", dataType = DataType.STRING)
    private String path;

    @ApiModelProperty(value = "资源路径", dataType = DataType.STRING)
    private String url;

    @ApiModelProperty(value = "排序", required = true, dataType = DataType.INTEGER)
    @NotNull(message="页码数不能为空")
    private Integer sort;

    @ApiModelProperty(value = "描述", dataType = DataType.STRING)
    private String description;

    @ApiModelProperty(value = "菜单状态", dataType = DataType.INTEGER)
    private Integer status;
}
