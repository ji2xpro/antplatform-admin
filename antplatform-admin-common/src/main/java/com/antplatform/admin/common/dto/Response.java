package com.antplatform.admin.common.dto;

import com.antplatform.admin.common.enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: maoyan
 * @date: 2020/1/22 11:26:11
 * @description:
 */
@Data
@NoArgsConstructor
public class Response<T> extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return this.getCode() == ResponseCode.SUCCESS.getCode();
    }

}
