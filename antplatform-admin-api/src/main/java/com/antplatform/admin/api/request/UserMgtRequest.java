package com.antplatform.admin.api.request;

import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:37:45
 * @description:
 */
@Data
public class UserMgtRequest {
    String username;
    String password;
    String token;
}
