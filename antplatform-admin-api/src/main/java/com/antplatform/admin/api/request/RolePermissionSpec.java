package com.antplatform.admin.api.request;

import com.antplatform.admin.api.dto.RolePermissionDTO;
import lombok.Data;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/18 19:26:35
 * @description:
 */
@Data
public class RolePermissionSpec {

    private Integer roleId;

    private List<RolePermissionDTO> addResources;

    private List<RolePermissionDTO> delResources;
}
