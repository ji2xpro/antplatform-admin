package com.antplatform.admin.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: maoyan
 * @date: 2020/1/6 17:53:27
 * @description:
 */
@Data
@NoArgsConstructor
public class Paging {
    private int pageNo;
    private int pageSize;
    private int totalHits;
    private boolean hasMore;

    public Paging(int pageNo, int pageSize, int totalHits,boolean hasMore) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalHits = totalHits;
        this.hasMore = hasMore;
    }
}