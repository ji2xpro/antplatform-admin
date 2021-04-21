package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:43:01
 * @description:
 */
@Data
public class MenuDTO extends TreeDTO {
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
