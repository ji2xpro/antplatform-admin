package com.antplatform.admin.web.entity.system.permission;

import lombok.Data;

import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/9/2 10:44:12
 * @description:
 */
@Data
public class RoleVO {
    private Integer id;

    private String name;

    private String keypoint;

    private Integer type;

    private Date createTime;


    private String remark;

    private Integer status;

    private PermissionVO permissionVO;
}
