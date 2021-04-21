package com.antplatform.admin.api.request;

import lombok.Data;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:26:35
 * @description:
 */
@Data
public class AuthoritySpec {
    private Integer id;

    private String name;

    private Integer parentId;

    private String code;

    private String path;

    private Integer type;

    private Integer sort;

    private String description;

    private Integer status;
}
