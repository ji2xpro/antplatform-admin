package com.antplatform.admin.biz.infrastructure.shiro.account;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: maoyan
 * @date: 2021/3/15 14:08:49
 * @description:
 */
@Data
public class CurrentUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long userId;          // 主键ID
    public String account;      // 账号
    public String name;         // 姓名
    public Long orgId;      // 组织ID

    public CurrentUser(String account) {
        this.account = account;
    }

}
