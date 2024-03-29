package com.antplatform.admin.common.exception;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统一异常处理类<br>
 * 捕获程序所有异常，针对不同异常，采取不同的处理方式
 * @author: maoyan
 * @date: 2020/9/25 16:42:03
 * @description:
 */
@RestControllerAdvice
@Slf4j
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
     * 捕捉参数校验的异常
     *
     * 请求参数校验（Path Variables 和 Request Parameters）参数校验失败后，会抛出 ConstraintViolationException 异常
     * 其余参数校验失败后，会抛出 MethodArgumentNotValidException 异常或 BindException 异常
     */
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public AjaxResult handler(Exception e) {
        StringBuffer stringBuffer = new StringBuffer();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
            violations.forEach(x -> stringBuffer.append(x.getMessage()).append(";"));
            return AjaxResult.createFailedResult(AjaxCode.INVALID_PARAMS, stringBuffer.toString());
        }
        BindException bindException = (BindException) e;
        BindingResult bindingResult = bindException.getBindingResult();
        return AjaxResult.createFailedResult(AjaxCode.INVALID_PARAMS,buildMessages(bindingResult));

//        List<ObjectError> allErrors = bindException.getBindingResult().getAllErrors();
//        allErrors.forEach(msg -> stringBuffer.append(msg.getDefaultMessage()).append(";"));
//        return AjaxResult.createFailedResult(AjaxCode.INVALID_PARAMS,stringBuffer.toString());
    }

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handler(HttpServletRequest request, Throwable ex) {
        //获取异常信息,获取异常堆栈的完整异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        //日志输出异常详情
        log.error(sw.toString());
        return AjaxResult.createFailedResult(getStatus(request).value(),"服务异常，请稍后再试: " + ex.getMessage());
    }

    //文件上传文件大小超出限制
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public AjaxResult<Map<String,Object>> fileSizeException(MaxUploadSizeExceededException exception) {
        log.error("文件太大，上传失败",exception);
        return AjaxResult.createFailedResult(AjaxCode.BUSINESS_ERROR,"只允许上传不大于"+exception.getMaxUploadSize()+"的文件");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    private String buildMessages(BindingResult result) {
        StringBuilder resultBuilder = new StringBuilder();

        List<ObjectError> errors = result.getAllErrors();
        if (errors != null && errors.size() > 0) {
            for (ObjectError error : errors) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    String fieldName = fieldError.getField();
                    String fieldErrMsg = fieldError.getDefaultMessage();
                    resultBuilder.append(fieldName).append(" ").append(fieldErrMsg).append(";");
                }
            }
        }
        return resultBuilder.toString();
    }
}
