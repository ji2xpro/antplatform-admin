package com.antplatform.admin.web.entity.system.resource;

import lombok.Data;

import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/9/2 10:44:12
 * @description:
 */
@Data
public class UserVO {
    private Integer id;

    private String name;

    private String nickname;

    private String mobile;

    private String email;

    private String roleName;

    private Integer status;

    private Date createTime;
}
