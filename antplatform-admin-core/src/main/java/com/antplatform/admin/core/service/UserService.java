package com.antplatform.admin.core.service;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserMgtRequest;
import com.antplatform.admin.core.model.User;

/**
 * @author: maoyan
 * @date: 2020/1/19 16:37:52
 * @description:
 */
public interface UserService {

//    boolean isExist(String username);
//
//    User getByName(String username);
//
//
//    User get(String username, String password);
//
//    void add(User user);

    UserDTO getByUsernameAndPassword(UserMgtRequest userMgtRequest);

    UserDTO getUserRole(UserMgtRequest userMgtRequest);

}
