package com.antplatform.admin.web.entity.system.resource;

import com.antplatform.admin.common.enums.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: maoyan
 * @date: 2020/8/31 16:37:48
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "OrganizationRequest对象", description = "组织查询信息")
public class OrganizationRequest {
    @ApiModelProperty(value = "序号", dataType = DataType.INTEGER)
    private Integer id;

    @ApiModelProperty(value = "父序号", dataType = DataType.INTEGER)
    private Integer parentId;

    @ApiModelProperty(value = "组织名称", dataType = DataType.STRING)
    private String name;

    @ApiModelProperty(value = "组织标识", dataType = DataType.STRING)
    private String keypoint;

    @ApiModelProperty(value = "类型", dataType = DataType.STRING)
    private Integer type;

    @ApiModelProperty(value = "组织级别", dataType = DataType.INTEGER)
    private Integer level;

    @ApiModelProperty(value = "组织图标", dataType = DataType.STRING)
    private String icon;

    @ApiModelProperty(value="省", dataType = DataType.STRING)
    private String province;

    @ApiModelProperty(value="市", dataType = DataType.STRING)
    private String city;

    @ApiModelProperty(value="区", dataType = DataType.STRING)
    private String area;

    @ApiModelProperty(value="街道", dataType = DataType.STRING)
    private String street;

    @ApiModelProperty(value="描述", dataType = DataType.STRING)
    private String description;

    @ApiModelProperty(value = "页码", required = true, dataType = DataType.INTEGER)
    @NotNull(message="页码数不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页数量", required = true, dataType = DataType.INTEGER)
    @NotNull(message="每页数量不能为空")
    private Integer pageSize;
}
