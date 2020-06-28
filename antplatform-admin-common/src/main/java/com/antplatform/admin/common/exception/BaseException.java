package com.antplatform.admin.common.exception;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:34:13
 * @description:
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -8703218751378470183L;
    private int code;
    private Throwable throwable;

    public BaseException(int errorCode, String msg, Throwable e) {
        super(msg, e);
        this.code = errorCode;
        this.throwable = e;
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(int errorCode, String msg) {
        super(msg);
        this.code = errorCode;
    }

    public int getCode() {
        return this.code;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}