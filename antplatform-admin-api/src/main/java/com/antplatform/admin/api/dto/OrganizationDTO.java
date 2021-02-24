package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:17:19
 * @description:
 */
@Data
public class OrganizationDTO extends TreeDTO {
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
