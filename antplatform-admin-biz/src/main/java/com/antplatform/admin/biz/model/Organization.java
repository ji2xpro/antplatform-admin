package com.antplatform.admin.biz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.antplatform.admin.biz.model.Organization")
@Data
@Table(name = "sys_organization")
public class Organization extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value="id主键")
    private Integer id;

    /**
     * 父组织id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value="parentId父组织id")
    private Integer parentId;

    /**
     * 组织名称
     */
    @Column(name = "name")
    @ApiModelProperty(value="name组织名称")
    private String name;

    /**
     * 组织类型,0-总公司,1-分公司,2-事业部,3-业务部
     */
    @Column(name = "type")
    @ApiModelProperty(value="type组织类型,0-总公司,1-分公司,2-事业部,3-业务部")
    private Integer type;

    /**
     * 组织标识
     */
    @Column(name = "keypoint")
    @ApiModelProperty(value="keypoint组织标识")
    private String keypoint;

    /**
     * 组织图标
     */
    @Column(name = "icon")
    @ApiModelProperty(value="icon组织图标")
    private String icon;

    /**
     * 组织级别（排序）
     */
    @Column(name = "level")
    @ApiModelProperty(value="level组织级别（排序）")
    private Integer level;

    /**
     * 省
     */
    @Column(name = "province")
    @ApiModelProperty(value="province省")
    private String province;

    /**
     * 市
     */
    @Column(name = "city")
    @ApiModelProperty(value="city市")
    private String city;

    /**
     * 区
     */
    @Column(name = "area")
    @ApiModelProperty(value="area区")
    private String area;

    /**
     * 街道
     */
    @Column(name = "street")
    @ApiModelProperty(value="street街道")
    private String street;

    /**
     * 描述
     */
    @Column(name = "description")
    @ApiModelProperty(value="description描述")
    private String description;

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
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", keypoint=").append(keypoint);
        sb.append(", icon=").append(icon);
        sb.append(", level=").append(level);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", area=").append(area);
        sb.append(", street=").append(street);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}