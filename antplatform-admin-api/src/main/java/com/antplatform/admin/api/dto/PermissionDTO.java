package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/8/20 10:15:27
 * @description:
 */
@Data
public class PermissionDTO extends TreeDTO {
//    private Integer id;
//
//    private Integer parentId;

    private String name;

    private String alias;

    private String icon;

    private String path;

    private Integer type;

    private String url;

    private String pageName;

    private Integer isCache;

    private Integer isDelete;

//    private List<PermissionDTO> children = new ArrayList<>();
}
