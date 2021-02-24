package com.antplatform.admin.web.entity.system.resource;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:19:03
 * @description:
 */
@Data
public class OrganizationVO extends TreeDTO {
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
