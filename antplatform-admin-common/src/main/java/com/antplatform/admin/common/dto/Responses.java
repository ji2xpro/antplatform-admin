package com.antplatform.admin.common.dto;

import com.antplatform.admin.common.enums.ResponseCode;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:29:51
 * @description:
 */
public abstract class Responses {
    public static <T> Response<T> of(T data) {
        Response<T> response = new Response<T>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> of(T data, String msg) {
        Response<T> response = of(data);
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> fail(int code, String msg) {
        if (code == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid code.");
        }
        if (msg != null && ResponseCode.SUCCESS.getMsg().equals(msg)) {
            throw new IllegalArgumentException("Invalid msg.");
        }
        Response<T> response = new Response<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> unknownInnerError() {
        return fail(ResponseCode.UNKNOWN_INNER_ERROR.getCode(), ResponseCode.UNKNOWN_INNER_ERROR.getMsg());
    }

    public static <T> Response<T> unsupportedRequest() {
        return fail(ResponseCode.UNSUPPORTED_REQUEST.getCode(), ResponseCode.UNSUPPORTED_REQUEST.getMsg());
    }

    public static <T> Response<T> requestTimeout() {
        return fail(ResponseCode.REQUEST_TIMEOUT.getCode(), ResponseCode.REQUEST_TIMEOUT.getMsg());
    }
}
