package com.antplatform.admin.web.entity.system.resource.request;

import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.common.enums.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:42:54
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "RolePermissionRequest对象", description = "角色权限查询信息")
public class RolePermissionRequest {

    /**
     * 需要操作的角色id
     */

    @ApiModelProperty(value = "角色id", dataType = DataType.INTEGER)
    @NotNull(message="角色id不能为空")
    private Integer roleId;

    /**
     * 添加的资源列表
     */
    @ApiModelProperty(value = "添加的权限列表", dataType = DataType.ARRAY)
    private List<RolePermissionDTO> addResources;

    /**
     * 删除的资源列表
     */
    @ApiModelProperty(value = "删除的权限列表", dataType = DataType.ARRAY)
    private List<RolePermissionDTO> delResources;

}
