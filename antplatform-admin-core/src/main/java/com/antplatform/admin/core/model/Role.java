package com.antplatform.admin.core.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sys_role")
public class Role extends BaseModel implements Serializable {
    /**
     * 主键 
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 角色介绍
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * 角色备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 状态,0-启用,-1禁用
     */
    @Column(name = "state")
    private Integer status;

    /**
     * 状态,0-未删除,1-删除
     */
    @Column(name = "deleted")
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", introduction=").append(introduction);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}