package com.antplatform.admin.web.biz.system.permission;

import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
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
}