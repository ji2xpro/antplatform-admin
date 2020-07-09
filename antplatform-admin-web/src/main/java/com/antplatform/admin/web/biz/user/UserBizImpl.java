package com.antplatform.admin.web.biz.user;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:20:13
 * @description:
 */
//@Component
@Slf4j
@Service("UserBiz")
public class UserBizImpl implements UserBiz{

    @Autowired
    private UserMgtApi userMgtApi;

    /**
     * 查询指定用户
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Response<UserDTO> queryUser(String username, String password) {
        UserMgtSpec userMgtSpec = new UserMgtSpec();
        userMgtSpec.setUsername(username);
        userMgtSpec.setPassword(password);
        try {
            return userMgtApi.queryUser(userMgtSpec);
        }catch (Exception e){
            log.error(String.format("invoke userMgtApi.queryUser exception, spec=%", userMgtSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 查询用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Response<UserDTO> queryUserRole(String token) {
        UserMgtSpec userMgtSpec = new UserMgtSpec();
        userMgtSpec.setToken(token);
        try {
            return userMgtApi.queryUserRole(userMgtSpec);
        }catch (Exception e){
            log.error(String.format("invoke userMgtApi.queryUserRole exception, spec=%", userMgtSpec), e);
            return Responses.fail(ResponseCode.REQUEST_TIMEOUT.getCode(), "查询用户详情服务超时");
        }
    }
}
