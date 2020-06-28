package com.antplatform.admin.common.dto;

import com.antplatform.admin.common.enums.ResponseCode;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:55:02
 * @description:
 */
public abstract class PagedResponses {

    public static <T> PagedResponse<T> of(List<T> records, int totalHits, boolean hasNext) {
        PagedResponse<T> response = new PagedResponse<T>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(records);
        response.setTotalHits(totalHits);
        response.setHasNext(hasNext);
        return response;
    }

    public static <T> PagedResponse<T> of(List<T> records, int totalHits) {
        PagedResponse<T> response = new PagedResponse<T>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(records);
        response.setTotalHits(totalHits);
        return response;
    }

    public static <T> PagedResponse<T> of(List<T> records, int totalHits, String msg) {
        PagedResponse<T> response = of(records, totalHits);
        response.setMsg(msg);
        return response;
    }

    public static <T> PagedResponse<T> fail(int code, String msg) {
        if (code == ResponseCode.SUCCESS.getCode()) throw new IllegalArgumentException("Invalid code.");
        if (msg != null && ResponseCode.SUCCESS.getMsg().equals(msg)) throw new IllegalArgumentException("Invalid msg.");
        PagedResponse<T> response = new PagedResponse<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> PagedResponse<T> unknownInnerError() {
        return fail(ResponseCode.UNKNOWN_INNER_ERROR.getCode(), ResponseCode.UNKNOWN_INNER_ERROR.getMsg());
    }

    public static <T> PagedResponse<T> unsupportedRequest() {
        return fail(ResponseCode.UNSUPPORTED_REQUEST.getCode(), ResponseCode.UNSUPPORTED_REQUEST.getMsg());
    }

    public static <T> PagedResponse<T> requestTimeout() {
        return fail(ResponseCode.REQUEST_TIMEOUT.getCode(), ResponseCode.REQUEST_TIMEOUT.getMsg());
    }
}
