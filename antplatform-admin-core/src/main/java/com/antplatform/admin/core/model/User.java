package com.antplatform.admin.core.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sys_user")
public class User extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "userName")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nickName")
    private String nickName;

    /**
     * 用户备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 状态,0-启用,1-禁用
     */
    @Column(name = "state")
    private Integer status;

    /**
     * 状态,0-未删除,1-删除
     */
    @Column(name = "deleted")
    private Integer deleted;

    /**
     * 用户头像
     */
    @Column(name = "avatar")
    private String avatar;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", nickName=").append(nickName);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append(", avatar=").append(avatar);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}