package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.LoginDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/7/9 17:01:42
 * @description:
 */
public interface LoginMgtApi {
    /**
     * 登录
     *
     * @param userSpec
     * @return
     */
    Response<LoginDTO> submitLogin(UserSpec userSpec);

    /**
     * 登出
     *
     * @return
     */
    Response<Boolean> submitLogout();

    /**
     * 校验username是否存在
     * @param username
     * @return
     */
    Response<Object> checkName(String username);

    /**
     * 注册
     *
     * @param userMgtSpec
     * @return
     */
    Response<String> register(UserMgtSpec userMgtSpec);

}
