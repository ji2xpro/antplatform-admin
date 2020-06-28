package com.antplatform.admin.common.exception;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:52:28
 * @description:
 */
public class BusinessException extends BaseException {

    public BusinessException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(int errorCode, String msg) {
        super(errorCode, msg);
    }
}
