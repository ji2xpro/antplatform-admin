package com.antplatform.admin.web.entity.system.resource.vo;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:57:29
 * @description:
 */
@Data
public class AuthorityVO extends TreeDTO {
    private String name;

    private String code;

    private String path;

    private Integer type;

    private int sort;
}
