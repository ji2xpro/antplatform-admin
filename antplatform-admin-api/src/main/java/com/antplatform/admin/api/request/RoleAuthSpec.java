package com.antplatform.admin.api.request;

import com.antplatform.admin.api.dto.RoleAuthDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import lombok.Data;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/18 19:26:35
 * @description:
 */
@Data
public class RoleAuthSpec {

    private Integer roleId;

    private List<RoleAuthDTO> addAuths;

    private List<RoleAuthDTO> delAuths;
}
