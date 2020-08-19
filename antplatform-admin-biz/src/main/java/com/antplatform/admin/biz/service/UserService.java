package com.antplatform.admin.biz.service;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.User;

/**
 * @author: maoyan
 * @date: 2020/1/19 16:37:52
 * @description:
 */
public interface UserService{
    /**
     * 查询指定用户
     * @param userMgtSpec
     * @return
     */
    User getUser(UserMgtSpec userMgtSpec);

    /**
     * 通过userId主键查找
     * @param userId
     * @return
     */
    User queryByUserId(Integer userId);


    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     * 查询用户信息
     * @param userMgtSpec
     * @return
     */
    UserDTO getUserRole(UserMgtSpec userMgtSpec);


    /**
     * 查询用户信息
     * @param userSpec
     * @return
     */
    User findBySpec(UserSpec userSpec);

}
