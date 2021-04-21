package com.antplatform.admin.web.entity.system.resource.request;

import com.antplatform.admin.common.enums.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:42:54
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "PermissionRequest对象", description = "权限查询信息")
public class PermissionRequest {

    @ApiModelProperty(value = "父id", dataType = DataType.INTEGER)
    private Integer parentId;
}
