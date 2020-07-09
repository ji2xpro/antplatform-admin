package com.antplatform.admin.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/7/9 11:27:36
 * @description:
 */
@Data
public abstract class Request extends BaseDTO {

    private Date createTime;
}
