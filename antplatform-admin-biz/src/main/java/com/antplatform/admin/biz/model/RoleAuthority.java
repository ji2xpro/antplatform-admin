package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.RoleAuthority")
@Data
@Table(name = "sys_role_authority")
public class RoleAuthority extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 角色Id
     */
    @Column(name = "role_id")
    @ApiModelProperty(value="roleId角色Id")
    private Integer roleId;

    /**
     * 权限Id
     */
    @Column(name = "authority_id")
    @ApiModelProperty(value="authorityId权限Id")
    private Integer authorityId;

    /**
     * 是否删除,0-未删除,1-删除
     */
    @Column(name = "is_delete")
    @ApiModelProperty(value="isDelete是否删除,0-未删除,1-删除")
    private Integer isDelete;

    /**
     * 创建人
     */
    @Column(name = "creator")
    @ApiModelProperty(value="creator创建人")
    private String creator;

    /**
     * 修改人
     */
    @Column(name = "editor")
    @ApiModelProperty(value="editor修改人")
    private String editor;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", authorityId=").append(authorityId);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", creator=").append(creator);
        sb.append(", editor=").append(editor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}