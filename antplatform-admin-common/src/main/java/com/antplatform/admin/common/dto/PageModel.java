package com.antplatform.admin.common.dto;

import lombok.Data;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/7/8 19:34:52
 * @description:
 */
@Data
public class PageModel extends RowBounds implements ResultHandler,Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Total records size
     */
    private int recordCount;

    /**
     * The number of records of per page
     */
    private int pageSize;

    /**
     * Current page
     */
    private int page = 1;

    /**
     * Records
     */
    private List<?> records;

    public PageModel(int page, int pageSize) {
        super(page > 0 ? (page - 1) * pageSize : 0, pageSize);
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Paginate [recordCount=" + recordCount + ", pageSize=" + pageSize + ", page=" + page + ", records="
                + records ;
    }

    @Override
    public void handleResult(ResultContext context) {
        // do nothing
    }
}