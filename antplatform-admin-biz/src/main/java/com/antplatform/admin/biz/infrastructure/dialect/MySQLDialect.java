package com.antplatform.admin.biz.infrastructure.dialect;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.antplatform.admin.biz.infrastructure.tkmybatis.MysqlCountOutputVisitor;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/1 20:02:19
 * @description:
 */
public class MySQLDialect extends Dialect {

    @Override
    public String getCountSql(String sql) {
        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();

        // 将AST通过visitor输出
        StringBuilder out = new StringBuilder();
        MysqlCountOutputVisitor visitor = new MysqlCountOutputVisitor(out);

        for (SQLStatement stmt : stmtList) {
            if (stmt instanceof SQLSelectStatement) {
                stmt.accept(visitor);
                out.append(";");
            }
        }

        return out.toString();
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {
            return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
        } else {
            return sql + " limit " + limitPlaceholder;
        }
    }
}