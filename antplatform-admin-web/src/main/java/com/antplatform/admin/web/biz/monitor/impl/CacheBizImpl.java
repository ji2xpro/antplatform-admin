package com.antplatform.admin.web.biz.monitor.impl;

import com.antplatform.admin.api.CacheMgtApi;
import com.antplatform.admin.api.dto.CacheDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.biz.monitor.CacheBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2021/3/31 17:54:13
 * @description:
 */
@Slf4j
@Service("CacheBiz")
public class CacheBizImpl implements CacheBiz {

    @Autowired
    private CacheMgtApi cacheMgtApi;

    /**
     * 查询缓存信息
     *
     * @return
     */
    @Override
    public Response<CacheDTO> queryCacheInfo() {
        try {
            return cacheMgtApi.findBySpec();
        } catch (Exception e) {
            log.error(String.format("invoke cacheMgtApi.findBySpec exception, spec = %s", null), e);
            return Responses.requestTimeout();
        }
    }
}
