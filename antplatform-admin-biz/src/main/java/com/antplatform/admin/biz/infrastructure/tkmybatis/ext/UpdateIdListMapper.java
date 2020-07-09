package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/7/8 19:31:31
 * @description:
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface UpdateIdListMapper<T, PK> extends IdListMapper<T, PK> {

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    @SelectProvider(type = UpdateIdListProvider.class, method = "dynamicSQL")
    void bulkUpdateByExampleSelective(@Param("list") List<T> list);
}
