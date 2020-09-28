package com.antplatform.admin.web.biz.basic;

import com.antplatform.admin.api.dto.LoginDTO;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.web.entity.basic.LoginRequest;

/**
 * @author: maoyan
 * @date: 2020/7/9 16:57:15
 * @description:
 */
public interface LoginBiz {
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
     * @param loginRequest
     * @return
     */
    Response<String> register(LoginRequest loginRequest);
}
