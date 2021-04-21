package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.CacheDTO;
import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:49:50
 * @description:
 */
public interface CacheMgtApi {
    /**
     * 查询缓存信息
     *
     * @return
     */
    Response<CacheDTO> findBySpec();
}
