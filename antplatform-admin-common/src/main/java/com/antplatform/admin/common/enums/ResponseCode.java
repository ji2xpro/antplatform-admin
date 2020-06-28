package com.antplatform.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:29:53
 * @description:
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(1000, "success"),

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
