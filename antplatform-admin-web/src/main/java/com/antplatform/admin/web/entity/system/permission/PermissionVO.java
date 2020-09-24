package com.antplatform.admin.web.entity.system.permission;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/16 11:43:2 * @description:
 */
@Data
public class PermissionVO{
    private Integer id;

    private Integer parentId;

    private String name;

    private String alias;

    private String icon;

    private Integer type;

    private String path;

    private String url;

    private String pageName;

    private Integer isCache;

    private Integer isDelete;

    private List<PermissionVO> children = new ArrayList<>();
}
