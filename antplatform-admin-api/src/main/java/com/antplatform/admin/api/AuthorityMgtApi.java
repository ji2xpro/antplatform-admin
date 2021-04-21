package com.antplatform.admin.api;

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
 * @date: 2021/3/5 17:49:50
 * @description:
 */
public interface AuthorityMgtApi {
    /**
     * 查询所有权限
     * @return
     */
    Response<List<AuthorityDTO>> findBySpec();

    /**
     * 查询角色权限列表
     * @param roleId
     * @return
     */
    Response<Collection<AuthorityDTO>> findBySpec(int roleId);

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
