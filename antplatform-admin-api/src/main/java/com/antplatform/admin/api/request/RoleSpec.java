package com.antplatform.admin.api.request;

import lombok.Data;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/12 16:30:42
 * @description:
 */
@Data
public class RoleSpec {
    private Integer roleId;

    private String name;

    private String keypoint;

    private String remark;

    private Integer status;

    private Integer type;

    private Collection<Integer> roleIds;


}
