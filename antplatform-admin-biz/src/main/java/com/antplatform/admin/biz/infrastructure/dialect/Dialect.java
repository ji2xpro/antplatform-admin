package com.antplatform.admin.biz.infrastructure.dialect;

/**
 * @author: maoyan
 * @date: 2020/9/1 16:08:12
 * @description:
 */
public abstract class Dialect {

    public abstract String getCountSql(String sql);

    public String getLimitSql(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    public abstract String getLimitString(String sql, int offset, String offsetPlaceholder, int limit,
                                          String limitPlaceholder);

}
