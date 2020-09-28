package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:41:57
 * @description:
 */
@Data
public class UserDTO extends BaseDTO {
//    private String token;

    private Collection<String> roles;
    private Collection<PermissionDTO> resources;
    private String introduction;
    private String avatar;
    private String name;

}
