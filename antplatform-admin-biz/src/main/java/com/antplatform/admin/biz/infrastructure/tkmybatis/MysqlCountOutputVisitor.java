package com.antplatform.admin.biz.infrastructure.tkmybatis;

import com.alibaba.druid.sql.ast.SQLSetQuantifier;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/9/1 20:06:14
 * @description:
 */
public class MysqlCountOutputVisitor extends MySqlOutputVisitor {
    private boolean subSelect = false;

    public MysqlCountOutputVisitor(Appendable appender) {
        super(appender);
    }

    @Override
    public boolean visit(MySqlSelectQueryBlock x) {
        if (x.getOrderBy() != null) {
            x.getOrderBy().setParent(x);
        }

        boolean rewriteDistinct = false;
        if (x.getSelectList() != null) {
            rewriteDistinct = visitSelectItems(x.getSelectList(), SQLSetQuantifier.DISTINCT == x.getDistionOption());
        }

        if (x.getFrom() != null) {
            println();
            print0(ucase ? "FROM " : "from ");
            x.getFrom().accept(this);
        }

        if (x.getWhere() != null) {
            println();
            print0(ucase ? "WHERE " : "where ");
            x.getWhere().setParent(x);
            x.getWhere().accept(this);
        }

        if (x.getGroupBy() != null) {
            println();
            x.getGroupBy().accept(this);
        }

        if (x.getOrderBy() != null) {
            println();
            x.getOrderBy().accept(this);
        }

        if (rewriteDistinct) {
            print0(") ZebraDaoDistinctTable");
        }

        return false;
    }

    private boolean visitSelectItems(List<SQLSelectItem> selectItems, boolean distinct) {
        boolean rewriteDistinct = false;
        if (this.subSelect) {
            // sub select
            print0(ucase ? "SELECT " : "select ");
            if (distinct) {
                print0(ucase ? "DISTINCT " : "distinct ");
            }
            printSelectItems(selectItems);
        } else {
            if (distinct) {
                // select distinct a,b,... from xxx
                print0(ucase ? "SELECT COUNT(*) FROM (SELECT DISTINCT " : "select count(*) from (select distinct ");
                printSelectItems(selectItems);
                rewriteDistinct = true;
            } else {
                // normal select
                print0(ucase ? "SELECT COUNT(*) " : "select count(*) ");
            }
        }
        this.subSelect = true;
        return rewriteDistinct;
    }

    private void printSelectItems(List<SQLSelectItem> selectItems) {
        for (int i = 0; i < selectItems.size(); ++i) {
            SQLSelectItem item = selectItems.get(i);
            if (i > 0) {
                print0(",");
            }
            item.accept(this);
        }
    }

}
