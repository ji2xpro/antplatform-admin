package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/7/8 19:21:03
 * @description:
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface ExpandedIdListMapper<T, PK> extends IdListMapper<T, PK> {

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param idList
     * @return
     */
    @SelectProvider(type = ExpandedIdListProvider.class, method = "dynamicSQL")
    List<T> selectByIdListWithoutBlob(@Param("idList") List<PK> idList);
}
