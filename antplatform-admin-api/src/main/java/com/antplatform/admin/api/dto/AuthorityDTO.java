package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:43:01
 * @description:
 */
@Data
public class AuthorityDTO extends TreeDTO {
    private String name;

    private Integer parentId;

    private String code;

    private String path;

    private Integer type;

    private Integer sort;

    private String description;

    private Integer status;
}
