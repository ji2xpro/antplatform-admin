package com.antplatform.admin.common.exception;

import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(UnauthorizedException.class)
    public AjaxResult<?> handle401(UnauthorizedException e) {
        //获取错误中中括号的内容
        String message = e.getMessage();
        String msg = message.substring(message.indexOf("[")+1,message.indexOf("]"));
        //判断是角色错误还是权限错误
        if (message.contains("role")) {
            msg = "对不起，您没有" + msg + "角色";
        } else if (message.contains("permission")) {
            msg = "对不起，您没有" + msg + "权限";
        } else {
            msg = "对不起，您的权限有误";
        }
        return AjaxResult.createFailedResult(AjaxCode.NO_PERMISSION,msg);
    }

    /**
     * 捕捉shiro的异常
     * @param
     * @return
     */
    @ExceptionHandler(value = ShiroException.class)
    public AjaxResult handleException(ShiroException e) {
        return AjaxResult.createFailedResult(AjaxCode.UNLOGIN_CODE,"登录失效，请重新登录");
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
