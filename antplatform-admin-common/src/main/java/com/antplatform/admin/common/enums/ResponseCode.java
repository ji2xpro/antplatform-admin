package com.antplatform.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:29:53
 * @description:
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(1000, "success"),

    ERROR(500,"参数错误"),

    /**
     * 验证码错误
     */
    INVALID_RE_VCODE(10000011, "验证码错误"),
    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD(10000003, "账号或密码错误"),
    /**
     * 用户已被锁定
     */
    USER_LOCKED(10000002,"用户已被锁定，禁止登录"),
    /**
     * 用户不存在
     */
    INVALID_USER(10000001, "用户不存在"),

    /**
     * 角色不存在
     */
    INVALID_ROLE(10000004, "角色不存在"),

    UNKNOWN_INNER_ERROR(2000, "unknown inner error"),
    UNSUPPORTED_REQUEST(2001, "unsupported request"),
    REQUEST_TIMEOUT(2002, "request timeout"),
    VALIDATION_ERROR(400000, "params validate error"),
    COMMUNICATION_ERROR(500000, "rpc/http invoke error"),
    BUSINESS_ERROR(600000, "generic business exception");

    private int code;
    private String msg;

    public static ResponseCode valueOf(int code){
        for(ResponseCode responseCode : ResponseCode.values()){
            if (code == responseCode.code) {
                return responseCode;
            }
        }
        throw new IllegalArgumentException("Invalid value for ResponseCode: " + code + ".");
    }
}
