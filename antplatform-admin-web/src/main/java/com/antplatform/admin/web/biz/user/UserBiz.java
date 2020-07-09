package com.antplatform.admin.web.biz.user;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.common.dto.Response;

/**
 * 用户模块
 *
 * @author: maoyan
 * @date: 2020/1/22 10:51:31
 * @description:
 */
public interface UserBiz {
    /**
     * 查询指定用户
     *
     * @param username
     * @param password
     * @return
     */
    Response<UserDTO> queryUser(String username, String password);

    /**
     * 查询用户信息
     *
     * @param token
     * @return
     */
    Response<UserDTO> queryUserRole(String token);
}
