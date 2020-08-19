package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:41:57
 * @description:
 */
@Data
public class UserDTO extends BaseDTO {
    String token;

    Collection<String> roles;
    String introduction;
    String avatar;
    String name;

}
