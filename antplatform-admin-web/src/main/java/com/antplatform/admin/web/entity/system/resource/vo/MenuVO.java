package com.antplatform.admin.web.entity.system.resource.vo;

import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:57:29
 * @description:
 */
@Data
public class MenuVO extends TreeDTO {
    private String name;

    private String alias;

    private String icon;

    private String path;

    private String url;

    private int authorityId;

    private int sort;

    private String description;

    private int status;
}
