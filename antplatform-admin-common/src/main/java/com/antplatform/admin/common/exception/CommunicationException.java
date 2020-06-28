package com.antplatform.admin.common.exception;

/**
 * 通信异常(rpc/http异常）
 * @author: maoyan
 * @date: 2020/1/22 15:51:29
 * @description:
 */
public class CommunicationException extends BaseException {

    public CommunicationException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }

    public CommunicationException(String msg) {
        super(msg);
    }

    public CommunicationException(int errorCode, String msg) {
        super(errorCode, msg);
    }
}