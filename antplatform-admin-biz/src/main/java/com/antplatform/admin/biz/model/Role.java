package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.Role")
@Data
@Table(name = "sys_role")
public class Role extends BaseModel implements Serializable {
    /**
     * 主键 
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键 ")
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    @ApiModelProperty(value="name角色名称")
    private String name;

    /**
     * 角色标识
     */
    @Column(name = "keypoint")
    @ApiModelProperty(value="keypoint角色标识")
    private String keypoint;

    /**
     * 类型,0-超级管理员,1-管理员,2-普通用户
     */
    @Column(name = "type")
    @ApiModelProperty(value="type类型,0-超级管理员,1-管理员,2-普通用户")
    private Integer type;

    /**
     * 介绍
     */
    @Column(name = "introduction")
    @ApiModelProperty(value="introduction介绍")
    private String introduction;

    /**
     * 备注
     */
    @Column(name = "remark")
    @ApiModelProperty(value="remark备注")
    private String remark;

    /**
     * 状态,0-启用,1-停用
     */
    @Column(name = "status")
    @ApiModelProperty(value="status状态,0-启用,1-停用")
    private Integer status;

    /**
     * 是否删除,0-未删除,1-删除
     */
    @Column(name = "is_delete")
    @ApiModelProperty(value="isDelete是否删除,0-未删除,1-删除")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", keypoint=").append(keypoint);
        sb.append(", type=").append(type);
        sb.append(", introduction=").append(introduction);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}