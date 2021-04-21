package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.AuthorityDTO;
import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.request.AuthoritySpec;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:46:56
 * @description:
 */
public interface AuthorityBiz {

    /**
     * 查询所有权限
     *
     * @return
     */
    Response<List<AuthorityDTO>> queryAuthorities();

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    Response<Collection<AuthorityDTO>> queryRoleAuth(int roleId);

    /**
     * 保存权限信息
     *
     * @param authoritySpec
     * @return
     */
    Response<Boolean> save(AuthoritySpec authoritySpec);

    /**
     * 删除权限信息
     *
     * @param authoritySpec
     * @return
     */
    Response<Boolean> delete(AuthoritySpec authoritySpec);
}
