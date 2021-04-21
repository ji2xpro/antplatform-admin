package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:49:50
 * @description:
 */
public interface ServerMgtApi {
    /**
     * 查询服务信息
     *
     * @return
     */
    Response<ServerDTO> findBySpec();
}
