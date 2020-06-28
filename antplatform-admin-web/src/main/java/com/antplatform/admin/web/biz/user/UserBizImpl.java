package com.antplatform.admin.web.biz.user;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtRequest;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:20:13
 * @description:
 */
@Component
@Log4j2
public class UserBizImpl implements UserBiz{

    @Autowired
    private UserMgtApi userMgtApi;

    @Override
    public Response<UserDTO> queryUser(String username, String password) {
        UserMgtRequest userMgtRequest = new UserMgtRequest();
        userMgtRequest.setUsername(username);
        userMgtRequest.setPassword(password);
        try {
            return userMgtApi.queryUser(userMgtRequest);
        }catch (Exception e){
            log.error(String.format("invoke projectMgtApi.saveOrUpdateDetail exception, projectId:%s",
                    username), e);
            return Responses.fail(ResponseCode.REQUEST_TIMEOUT.getCode(), "保存项目演出详情服务超时");
        }
    }

    @Override
    public Response<UserDTO> queryUserRole(String token) {
        UserMgtRequest userMgtRequest = new UserMgtRequest();
        userMgtRequest.setToken(token);
        try {
            return userMgtApi.queryUserRole(userMgtRequest);
        }catch (Exception e){
            log.error(String.format("invoke projectMgtApi.saveOrUpdateDetail exception, projectId:%s",
                    token), e);
            return Responses.fail(ResponseCode.REQUEST_TIMEOUT.getCode(), "保存项目演出详情服务超时");
        }
    }
}
