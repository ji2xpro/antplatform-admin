package com.antplatform.admin.common.dto;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 20:05:23
 * @description:
 */
public class PageModelHelper {
    public static boolean hasNext(PageModel pageModel) {
        return CollectionUtils.isNotEmpty(pageModel.getRecords())
                && ((pageModel.getPage() - 1) * pageModel.getPageSize() + pageModel.getRecords().size()) < pageModel
                .getRecordCount();
    }

    public static PageModel build(List<?> records, int page, int pageSize, int totalSize) {
        PageModel model = new PageModel(page, pageSize);
        model.setRecords(records);
        model.setRecordCount(totalSize);
        return model;
    }
}
