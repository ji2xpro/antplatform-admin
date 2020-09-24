package com.antplatform.admin.web.entity.system.permission;

import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/9/18 10:37:29
 * @description:
 */
@Data
public class RolePermissionVO {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private String name;

    private Integer Type;
}
