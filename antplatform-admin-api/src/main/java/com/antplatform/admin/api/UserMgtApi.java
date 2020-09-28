package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:28:38
 * @description:
 */
public interface UserMgtApi {
    /**
     * 查询用户信息
     * @param id
     * @return
     */
    Response<UserDTO> queryUserInfo(Integer id);

}
