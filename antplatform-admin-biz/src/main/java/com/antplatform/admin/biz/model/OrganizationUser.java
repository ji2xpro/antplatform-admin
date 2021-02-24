package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.OrganizationUser")
@Data
@Table(name = "sys_organization_user")
public class OrganizationUser extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 组织Id
     */
    @Column(name = "organization_id")
    @ApiModelProperty(value="organizationId组织Id")
    private Integer organizationId;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value="userId用户Id")
    private Integer userId;

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
        sb.append(", organizationId=").append(organizationId);
        sb.append(", userId=").append(userId);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}