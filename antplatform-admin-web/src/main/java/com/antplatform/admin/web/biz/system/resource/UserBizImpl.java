package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.UserMgtApi;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
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

    /**
     * 分页查询用户列表
     *
     * @param userPageSpec
     * @return
     */
    @Override
    public PagedResponse<UserDTO> queryUserPage(UserPageSpec userPageSpec) {
        try {
            return userMgtApi.findPageBySpec(userPageSpec);
        } catch (Exception e) {
            log.error(String.format("invoke userMgtApi.findPageBySpec exception, spec = %s", userPageSpec), e);
            return PagedResponses.requestTimeout();
        }
    }
}
