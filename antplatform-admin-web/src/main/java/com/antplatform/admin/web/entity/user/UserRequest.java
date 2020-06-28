package com.antplatform.admin.web.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/1/21 17:59:57
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    String username;
    String password;
}
