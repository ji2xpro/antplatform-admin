package com.antplatform.admin.api.request;

import lombok.Data;
/**
 * @author: maoyan
 * @date: 2021/3/5 17:26:35
 * @description:
 */
@Data
public class MenuSpec {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer authorityId;

    private String icon;

    private String path;

    private String url;

    private Integer sort;

    private String description;

    private Integer status;
}
