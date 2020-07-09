package com.antplatform.admin.common.dto;

import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/7/9 11:27:13
 * @description:
 */
@Data
public abstract class PagedRequest extends Request {

    private int pageNo;

    private int pageSize;
}
