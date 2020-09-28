package com.antplatform.admin.web.biz.system.permission;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:20:13
 * @description:
 */
@Slf4j
@Service("UserBiz")
public class UserBizImpl implements UserBiz {

    @Autowired
    private UserMgtApi userMgtApi;

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public Response<UserDTO> queryUserInfo(Integer id) {
        try {
            return userMgtApi.queryUserInfo(id);
        }catch (Exception e){
            log.error(String.format("invoke userMgtApi.queryUserInfo exception, spec = %s", id), e);
            return Responses.fail(ResponseCode.REQUEST_TIMEOUT.getCode(), "查询用户详情服务超时");
        }
    }
}
