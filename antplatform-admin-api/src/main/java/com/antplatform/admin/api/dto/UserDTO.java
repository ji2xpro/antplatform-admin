package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:41:57
 * @description:
 */
@Data
public class UserDTO extends BaseDTO {

    private Collection<String> roles;
    private Collection<PermissionDTO> resources;
    private Collection<String> auth;
    private Collection<MenuDTO> menus;
    private String introduction;
    private String avatar;

    private Integer id;

    private Integer organizationId;

    private Integer organizationName;

    private String username;

    private String name;

    private String nickname;
    private String mobile;
    private String email;

    private String roleName;

    private Integer status;
    private Date createTime;
}
