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
public enum IsDeleteStatus {
    EXITS(0, "未删除"),
    DELETED(1, "已删除");

    private int code;
    private String desc;

    public static IsDeleteStatus valueOf(int code){
        for(IsDeleteStatus isDeleteStatus : IsDeleteStatus.values()){
            if (code == isDeleteStatus.code) {
                return isDeleteStatus;
            }
        }
        throw new IllegalArgumentException("Invalid value for IsDeleteStatus: " + code + ".");
    }
}
