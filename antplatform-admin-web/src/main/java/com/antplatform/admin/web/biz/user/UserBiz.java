package com.antplatform.admin.web.biz.user;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/1/22 10:51:31
 * @description:
 */
public interface UserBiz {
    Response<UserDTO> queryUser(String username,String password);

    Response<UserDTO> queryUserRole(String token);
}
