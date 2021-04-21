package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.Menu")
@Data
@Table(name = "sys_menu")
public class Menu extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 菜单名称
     */
    @Column(name = "name")
    @ApiModelProperty(value="name菜单名称")
    private String name;

    /**
     * 父菜单Id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value="parentId父菜单Id")
    private Integer parentId;

    /**
     * 菜单编号路径
     */
    @Column(name = "path_id")
    @ApiModelProperty(value="pathId菜单编号路径")
    private String pathId;

    /**
     * 权限Id
     */
    @Column(name = "authority_id")
    @ApiModelProperty(value="authorityId权限Id")
    private Integer authorityId;

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
     * 资源路径
     */
    @Column(name = "url")
    @ApiModelProperty(value="url资源路径")
    private String url;

    /**
     * 描述
     */
    @Column(name = "description")
    @ApiModelProperty(value="description描述")
    private String description;

    /**
     * 排序
     */
    @Column(name = "sort")
    @ApiModelProperty(value="sort排序")
    private Integer sort;

    /**
     * 状态,0-启用,1-停用
     */
    @Column(name = "status")
    @ApiModelProperty(value="status状态,0-启用,1-停用")
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
        sb.append(", pathId=").append(pathId);
        sb.append(", authorityId=").append(authorityId);
        sb.append(", icon=").append(icon);
        sb.append(", path=").append(path);
        sb.append(", url=").append(url);
        sb.append(", description=").append(description);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", isCache=").append(isCache);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}