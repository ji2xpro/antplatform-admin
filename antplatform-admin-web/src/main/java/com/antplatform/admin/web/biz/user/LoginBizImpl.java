package com.antplatform.admin.web.biz.user;

import com.antplatform.admin.api.LoginMgtApi;
import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.LoginDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.entity.user.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/7/9 16:59:00
 * @description:
 */
@Slf4j
@Service("LoginBiz")
public class LoginBizImpl implements LoginBiz{

    @Autowired
    private LoginMgtApi loginMgtApi;

    /**
     * 登录
     * @param userSpec
     * @return
     */
    @Override
    public Response<LoginDTO> submitLogin(UserSpec userSpec) {
        try {
            return loginMgtApi.submitLogin(userSpec);
        }catch (Exception e){
            log.error(String.format("invoke loginMgtApi.submitLogin exception, spec=%", userSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 登出
     *
     * @return
     */
    @Override
    public Response<Boolean> submitLogout() {
        try {
            return loginMgtApi.submitLogout();
        }catch (Exception e){
            log.error(String.format("invoke loginMgtApi.submitLogout exception, spec=%", ""), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 校验username是否存在
     *
     * @param username
     * @return
     */
    @Override
    public Response<Object> checkName(String username) {
        try {
            return loginMgtApi.checkName(username);
        }catch (Exception e){
            log.error(String.format("invoke loginMgtApi.submitLogout exception, spec=%", ""), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 注册
     *
     * @param userRequest
     * @return
     */
    @Override
    public Response<String> register(UserRequest userRequest) {
        try {
            UserMgtSpec userMgtSpec = new UserMgtSpec();
            userMgtSpec.setUsername(userRequest.getUsername());
            userMgtSpec.setPassword(userRequest.getPassword());
            return loginMgtApi.register(userMgtSpec);
        }catch (Exception e){
            log.error(String.format("invoke loginMgtApi.submitLogout exception, spec=%", ""), e);
            return Responses.requestTimeout();
        }
    }
}
