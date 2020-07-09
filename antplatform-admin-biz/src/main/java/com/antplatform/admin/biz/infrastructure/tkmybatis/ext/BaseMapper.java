package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author: maoyan
 * @date: 2020/7/8 18:21:38
 * @description:
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BaseMapper<T> extends
        Mapper<T>,
        InsertListMapper<T>,
        ExpandedIdListMapper<T, Long>,
        UpdateIdListMapper<T, Long>,
        PageModelMapper<T> {
}
