package com.antplatform.admin.web.biz.monitor.impl;

import com.antplatform.admin.api.ServerMgtApi;
import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.biz.monitor.ServerBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2021/3/29 20:44:32
 * @description:
 */
@Slf4j
@Service("ServerBiz")
public class ServerBizImpl implements ServerBiz {

    @Autowired
    private ServerMgtApi serverMgtApi;

    /**
     * 查询服务信息
     *
     * @return
     */
    @Override
    public Response<ServerDTO> queryServerInfo() {
        try {
            return serverMgtApi.findBySpec();
        } catch (Exception e) {
            log.error(String.format("invoke serverMgtApi.findBySpec exception, spec = %s", null), e);
            return Responses.requestTimeout();
        }
    }
}
