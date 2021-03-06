package com.antplatform.admin.common.result;

import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.enums.ResponseCode;
import lombok.Data;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2019/10/30 18:18:41
 * @description:
 */
@Data
public class AjaxResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 响应码
     */
    private int code;
    /**
     * 返回msg
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 分页信息
     */
    private Paging paging;
    /**
     * 额外信息
     */
    private Map<Object,Object> attrMaps;

    public AjaxResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public AjaxResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.attrMaps = getDefaultAttrMap();
    }

    public AjaxResult(int code, String msg, T data, Paging paging) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.paging = paging;
        this.attrMaps = getDefaultAttrMap();
    }

    public AjaxResult(int code,String msg, T data,Map<Object,Object> attrMaps) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (MapUtils.isNotEmpty(attrMaps)) {
            attrMaps.put("serverTime",System.currentTimeMillis());
        } else {
            attrMaps = getDefaultAttrMap();
        }
        this.attrMaps = attrMaps;
    }

    private Map<Object, Object> getDefaultAttrMap() {
        Map<Object, Object> map = new HashMap<>(1);
        map.put("serverTime",System.currentTimeMillis());
        return map;
    }

    public static <T> AjaxResult<T> createSuccessResult(T data) {
        return new AjaxResult<T>(AjaxCode.SUCCESS_CODE, StringUtils.EMPTY, data);
    }

    public static <T> AjaxResult<T> createSuccessResult(T data,Map<Object,Object> attrMaps) {
        return new AjaxResult<T>(AjaxCode.SUCCESS_CODE, StringUtils.EMPTY, data,attrMaps);
    }

    public static <T> AjaxResult<T> createSuccessResult(T data, Paging paging) {
        return new AjaxResult<T>(AjaxCode.SUCCESS_CODE, StringUtils.EMPTY, data, paging);
    }

    public static<T> AjaxResult<T> createFailedResult(T data,int code, String msg) {
        return new AjaxResult<T>(code, msg, data);
    }

    public static<T> AjaxResult<T> createFailedResult(int code, String msg) {
        return new AjaxResult<T>(code, msg, null);
    }

    public static<T> AjaxResult<T> createFailedResult(int code, String msg,Map<Object,Object> attrMaps) {
        return new AjaxResult<T>(code, msg, null,attrMaps);
    }

    public static<T> AjaxResult<T> createServiceErrorResult() {
        return new AjaxResult<T>(AjaxCode.ERROR_CODE,"服务异常,请稍后再试",null);
    }
    public static<T> AjaxResult<T> createSuccessResult(T data,String msg){
        return new AjaxResult<T>(AjaxCode.SUCCESS_CODE,msg,data);
    }

    public static<T> AjaxResult<T> createFailedResult(ResponseCode responseCode) {
        return new AjaxResult<T>(responseCode.getCode(), responseCode.getMsg(), null);
    }

    public boolean isSuccess(){
        return this.code==AjaxCode.SUCCESS_CODE;
    }
}

