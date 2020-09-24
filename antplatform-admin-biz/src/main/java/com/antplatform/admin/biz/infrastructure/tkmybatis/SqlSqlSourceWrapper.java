package com.antplatform.admin.biz.infrastructure.tkmybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: maoyan
 * @date: 2020/9/1 16:10:41
 * @description:
 */
public class SqlSqlSourceWrapper implements SqlSource {
    private BoundSql boundSql;

    public SqlSqlSourceWrapper(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return boundSql;
    }
}
