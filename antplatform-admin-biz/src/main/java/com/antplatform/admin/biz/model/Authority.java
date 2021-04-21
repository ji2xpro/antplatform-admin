package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.Authority")
@Data
@Table(name = "sys_authority")
public class Authority extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 权限名称
     */
    @Column(name = "name")
    @ApiModelProperty(value="name权限名称")
    private String name;

    /**
     * 权限编码
     */
    @Column(name = "code")
    @ApiModelProperty(value="code权限编码")
    private String code;

    /**
     * 父权限id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value="parentId父权限id")
    private Integer parentId;

    /**
     * 编号路径
     */
    @Column(name = "path_id")
    @ApiModelProperty(value="pathId编号路径")
    private String pathId;

    /**
     * 类型,0-模块,1-菜单,2-按钮,3-链接
     */
    @Column(name = "type")
    @ApiModelProperty(value="type类型,0-模块,1-菜单,2-按钮,3-链接")
    private Integer type;

    /**
     * 权限描述
     */
    @Column(name = "description")
    @ApiModelProperty(value="description权限描述")
    private String description;

    /**
     * 排序
     */
    @Column(name = "sort")
    @ApiModelProperty(value="sort排序")
    private Integer sort;

    /**
     * 状态,0-有效,1-无效
     */
    @Column(name = "status")
    @ApiModelProperty(value="status状态,0-有效,1-无效")
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
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", parentId=").append(parentId);
        sb.append(", pathId=").append(pathId);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", creator=").append(creator);
        sb.append(", editor=").append(editor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}