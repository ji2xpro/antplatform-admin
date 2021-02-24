package com.antplatform.admin.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/12 16:30:42
 * @description:
 */
@Data
public class OrganizationSpec {
    private Integer organizationId;

    private String name;

    private String keypoint;

    private Integer type;

    private String icon;

    private Integer level;

    private String province;

    private String city;

    private String area;

    private String street;

    private String description;
}
