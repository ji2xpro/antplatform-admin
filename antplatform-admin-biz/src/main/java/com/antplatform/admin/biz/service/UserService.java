package com.antplatform.admin.biz.service;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.biz.model.User;

/**
 * @author: maoyan
 * @date: 2020/1/19 16:37:52
 * @description:
 */
public interface UserService {
    /**
     * 查询指定用户
     * @param userMgtSpec
     * @return
     */
    User getUser(UserMgtSpec userMgtSpec);

    /**
     * 查询用户信息
     * @param userMgtSpec
     * @return
     */
    UserDTO getUserRole(UserMgtSpec userMgtSpec);

}
