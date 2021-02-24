package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

/**
 * 用户模块
 *
 * @author: maoyan
 * @date: 2020/1/22 10:51:31
 * @description:
 */
public interface UserBiz {
    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    Response<UserDTO> queryUserInfo(Integer id);

    /**
     * 分页查询用户列表
     *
     * @param userPageSpec
     * @return
     */
    PagedResponse<UserDTO> queryUserPage(UserPageSpec userPageSpec);
}
