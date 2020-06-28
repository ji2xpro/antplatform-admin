package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtRequest;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:28:38
 * @description:
 */
public interface UserMgtApi {
    Response<UserDTO> queryUser(UserMgtRequest userRequest);

    Response<UserDTO> queryUserRole(UserMgtRequest userMgtRequest);
}
