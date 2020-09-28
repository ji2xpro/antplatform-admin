package com.antplatform.admin.common.exception;

import com.antplatform.admin.common.result.AjaxResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类<br>
 * 捕获程序所有异常，针对不同异常，采取不同的处理方式
 * @author: maoyan
 * @date: 2020/9/25 16:42:03
 * @description:
 */
@RestControllerAdvice
public class ExceptionController {
    /**
     * 捕捉shiro的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public AjaxResult handleShiroException(Exception ex) {
        return AjaxResult.createFailedResult(5000, ex.getMessage());
    }

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult globalException(HttpServletRequest request, Throwable ex) {
        return AjaxResult.createFailedResult(getStatus(request).value(),"访问出错，无法访问: " + ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}