package com.antplatform.admin.web.entity.system.resource.vo;

import com.antplatform.admin.common.dto.TreeDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/16 11:43:20
 * @description:
 */
@Data
public class PermissionVO extends TreeDTO {
//    private Integer id;
//
//    private Integer parentId;

    private String name;

    private String alias;

    private String icon;

    private Integer type;

    private String path;

    private String url;

    private String pageName;

    private Integer isCache;

    private Integer isDelete;

//    private List<PermissionVO> children = new ArrayList<>();
}
