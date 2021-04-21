package com.antplatform.admin.web.biz.monitor;

import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2021/3/29 20:42:45
 * @description:
 */
public interface ServerBiz {
    /**
     * 查询服务信息
     * @return
     */
    Response<ServerDTO> queryServerInfo();

}
