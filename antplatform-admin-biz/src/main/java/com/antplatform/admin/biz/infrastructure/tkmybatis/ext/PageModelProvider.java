package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.provider.ExampleProvider;

/**
 * @author: maoyan
 * @date: 2020/7/8 19:36:16
 * @description:
 */
public class PageModelProvider extends ExampleProvider {

    public PageModelProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询全部结果
     *
     * @param ms
     * @return
     */
    public String selectByExampleAndPageModel(MappedStatement ms) {
        return super.selectByExample(ms);
    }
}