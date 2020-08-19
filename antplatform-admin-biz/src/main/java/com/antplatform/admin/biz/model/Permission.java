package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.Permission")
@Data
@Table(name = "sys_permission")
public class Permission extends BaseModel implements Serializable {
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
     * 权限父类ID
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value="parentId权限父类ID")
    private Integer parentId;

    /**
     * 权限点
     */
    @Column(name = "keypoint")
    @ApiModelProperty(value="keypoint权限点")
    private String keypoint;

    /**
     * 类型,0-模块,1-菜单,2-按钮,3-链接
     */
    @Column(name = "type")
    @ApiModelProperty(value="type类型,0-模块,1-菜单,2-按钮,3-链接")
    private Integer type;

    /**
     * 图标
     */
    @Column(name = "icon")
    @ApiModelProperty(value="icon图标")
    private String icon;

    /**
     * 路径
     */
    @Column(name = "path")
    @ApiModelProperty(value="path路径")
    private String path;

    /**
     * 网址
     */
    @Column(name = "url")
    @ApiModelProperty(value="url网址")
    private String url;

    /**
     * 优先级
     */
    @Column(name = "level")
    @ApiModelProperty(value="level优先级")
    private Integer level;

    /**
     * 介绍
     */
    @Column(name = "description")
    @ApiModelProperty(value="description介绍")
    private String description;

    /**
     * 状态,0-启用,-1禁用
     */
    @Column(name = "status")
    @ApiModelProperty(value="status状态,0-启用,-1禁用")
    private Integer status;

    /**
     * 是否缓存,0-缓存,1-不缓存
     */
    @Column(name = "is_cache")
    @ApiModelProperty(value="isCache是否缓存,0-缓存,1-不缓存")
    private Integer isCache;

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
        sb.append(", parentId=").append(parentId);
        sb.append(", keypoint=").append(keypoint);
        sb.append(", type=").append(type);
        sb.append(", icon=").append(icon);
        sb.append(", path=").append(path);
        sb.append(", url=").append(url);
        sb.append(", level=").append(level);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", isCache=").append(isCache);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}