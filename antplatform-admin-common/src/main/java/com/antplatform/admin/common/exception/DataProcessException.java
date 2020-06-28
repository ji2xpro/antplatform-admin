package com.antplatform.admin.common.exception;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:52:07
 * @description:
 */
public abstract class DataProcessException extends BaseException{

    public DataProcessException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }

    public DataProcessException(String msg) {
        super(msg);
    }

    public DataProcessException(int errorCode, String msg) {
        super(errorCode, msg);
    }
}
