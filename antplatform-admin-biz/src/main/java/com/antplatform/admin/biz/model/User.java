package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.User")
@Data
@Table(name = "sys_user")
public class User extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 账号
     */
    @Column(name = "username")
    @ApiModelProperty(value="username账号")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    @ApiModelProperty(value="password用户密码")
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name")
    @ApiModelProperty(value="name姓名")
    private String name;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    @ApiModelProperty(value="nickname昵称")
    private String nickname;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    @ApiModelProperty(value="mobile手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email")
    @ApiModelProperty(value="email邮箱")
    private String email;

    /**
     * 用户头像
     */
    @Column(name = "avatar")
    @ApiModelProperty(value="avatar用户头像")
    private String avatar;

    /**
     * 用户备注
     */
    @Column(name = "remark")
    @ApiModelProperty(value="remark用户备注")
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", nickname=").append(nickname);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", creator=").append(creator);
        sb.append(", editor=").append(editor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}