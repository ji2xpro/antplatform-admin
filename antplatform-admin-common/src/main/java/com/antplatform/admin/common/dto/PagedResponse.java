package com.antplatform.admin.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:53:11
 * @description:
 */
@Data
@NoArgsConstructor
public class PagedResponse<T> extends Response<List<T>> {

    private static final long serialVersionUID = 1L;
    private int totalHits;
    /**
     * 列表请求返回 标识是否有后续分页
     */
    private boolean hasNext;
}
