package com.antplatform.admin.core.port;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtRequest;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.common.exception.ExceptionHandler;
import com.antplatform.admin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:24:27
 * @description:
 */
@Service("UserMgtApi")
public class UserMgtService implements UserMgtApi {

    @Autowired
    private UserService userService;

    @Override
    public Response<UserDTO> queryUser(UserMgtRequest userMgtRequest) {
        UserDTO userDTO = userService.getByUsernameAndPassword(userMgtRequest);

        if (userDTO == null){
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(),"请输入正确的用户名或密码");
        }
        return Responses.of(userDTO);
    }

    @Override
    public Response<UserDTO> queryUserRole(UserMgtRequest userMgtRequest) {
        UserDTO userDTO = userService.getUserRole(userMgtRequest);

        if (userDTO == null){
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(),"");
        }
        return Responses.of(userDTO);
    }
}
