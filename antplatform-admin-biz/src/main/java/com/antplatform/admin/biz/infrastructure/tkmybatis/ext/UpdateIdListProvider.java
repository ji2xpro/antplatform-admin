package com.antplatform.admin.biz.infrastructure.tkmybatis.ext;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * 批量更新的SqlProvider
 *
 * @author: maoyan
 * @date: 2020/7/8 19:31:53
 * @description:
 */
public class UpdateIdListProvider extends IdListProvider {

    public UpdateIdListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String columns(Class<?> entityClass) {
        return ExpandedSqlHelper.selectColumnsWithoutBlob(entityClass);
    }

    /**
     * 根据Example更新非null字段
     *
     * @param ms
     * @return
     */
    public String bulkUpdateByExampleSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(" <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">");
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, "item", true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, "item", true));
        sql.append("</foreach>");
        return sql.toString();
    }


}