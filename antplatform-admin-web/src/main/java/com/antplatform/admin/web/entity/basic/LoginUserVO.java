package com.antplatform.admin.web.entity.basic;

import com.antplatform.admin.api.dto.PermissionDTO;
import lombok.Data;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/10/20 14:47:57
 * @description:
 */
@Data
public class LoginUserVO {
    private Collection<String> roles;
    private Collection<PermissionDTO> resources;
    private String introduction;
    private String avatar;
    private String name;
}
