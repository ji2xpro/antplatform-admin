package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import com.antplatform.admin.common.dto.PageModel;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.entity.Example;

/**
 * @author: maoyan
 * @date: 2020/7/8 19:32:37
 * @description:
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface PageModelMapper<T> {

    /**
     * zebra 分页
     *
     * @return
     */
    @SelectProvider(type = PageModelProvider.class, method = "dynamicSQL")
    void selectByExampleAndPageModel(PageModel pageModel, Example example);
}