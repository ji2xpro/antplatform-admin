package com.antplatform.admin.common.exception;

import com.antplatform.admin.common.log.SuppressLogStackTraceFilter;

/**
 * 参数校验错误
 * @author: maoyan
 * @date: 2020/1/22 15:33:51
 * @description:
 */
public class ValidationException extends BaseException {

    public ValidationException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }

    public ValidationException(int errorCode, String msg, Throwable e, int depth) {
        super(errorCode, msg, e);
        SuppressLogStackTraceFilter.assignDepthLogTrace(this, depth);
    }

    public ValidationException(int errorCode, String msg) {
        super(errorCode, msg);
    }

}
