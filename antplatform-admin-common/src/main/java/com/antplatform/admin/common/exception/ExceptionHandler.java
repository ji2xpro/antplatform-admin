package com.antplatform.admin.common.exception;

import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import lombok.extern.log4j.Log4j2;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:32:30
 * @description:
 */
@Log4j2
public class ExceptionHandler {
    public static final String MSG_FMT = "[{}]code:{},msg:{}";

    private ExceptionHandler() {
    }

    public static Response processFailureException(Exception exp) {
        if (exp instanceof IllegalArgumentException) {
//            exp = new ValidationException(ResponseCode.VALIDATION_ERROR.getCode(), exp.getMessage(), exp, LionUtils.getExceptionDepth());
        }
        if (exp instanceof ValidationException) {
            ValidationException e = (ValidationException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((ValidationException) exp).getCode(), exp);
            return Responses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof CommunicationException) {
            CommunicationException e = (CommunicationException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((CommunicationException) exp).getCode(), exp);
            return Responses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof DataProcessException) {
            DataProcessException e = (DataProcessException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((DataProcessException) exp).getCode(), exp);
            return Responses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof BusinessException) {
            BusinessException e = (BusinessException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((BusinessException) exp).getCode(), exp);
            return Responses.fail(e.getCode(), e.getMessage());
        }
        log.error("msg:{}", exp);
//        Cat.logErrorWithCategory("UnknownError", exp);
        return Responses.unknownInnerError();
    }

    public static PagedResponse resolve(Exception exp) {
        if (exp instanceof IllegalArgumentException) {
//            exp = new ValidationException(ResponseCode.VALIDATION_ERROR.getCode(), exp.getMessage(), exp, LionUtils.getExceptionDepth());
        }
        if (exp instanceof ValidationException) {
            ValidationException e = (ValidationException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((ValidationException) exp).getCode(), exp);
            return PagedResponses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof CommunicationException) {
            CommunicationException e = (CommunicationException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((CommunicationException) exp).getCode(), exp);
            return PagedResponses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof DataProcessException) {
            DataProcessException e = (DataProcessException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((DataProcessException) exp).getCode(), exp);
            return PagedResponses.fail(e.getCode(), e.getMessage());
        }
        if (exp instanceof BusinessException) {
            BusinessException e = (BusinessException) exp;
            log.error(MSG_FMT, ExceptionHandler.currentMethod(exp), ((BusinessException) exp).getCode(), exp);
            return PagedResponses.fail(e.getCode(), e.getMessage());
        }
        log.error("msg:{}", exp);
//        Cat.logErrorWithCategory("UnknownError", exp);
        return PagedResponses.unknownInnerError();
    }

    /**
     * Returns current method signature.
     */
    public static String currentMethod(final Exception exp) {
        StackTraceElement[] ste = exp.getStackTrace();
        int ndx = (ste.length > 1) ? 1 : 0;
        return ste[ndx].toString();
    }
}
