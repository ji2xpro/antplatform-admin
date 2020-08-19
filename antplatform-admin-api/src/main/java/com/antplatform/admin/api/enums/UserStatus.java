package com.antplatform.admin.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: maoyan
 * @date: 2020/7/10 10:36:33
 * @description:
 */
@AllArgsConstructor
@Getter
public enum UserStatus {
    NORMAL(0, "正常"),
    DISABLE(1, "禁用");


    private int code;
    private String desc;

    public static UserStatus valueOf(int code){
        for(UserStatus userStatus : UserStatus.values()){
            if (code == userStatus.code) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("Invalid value for UserStatus: " + code + ".");
    }
}
