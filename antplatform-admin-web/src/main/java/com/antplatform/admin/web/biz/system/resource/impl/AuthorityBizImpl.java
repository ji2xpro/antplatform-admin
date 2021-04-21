package com.antplatform.admin.web.biz.system.resource.impl;

import com.antplatform.admin.api.AuthorityMgtApi;
import com.antplatform.admin.api.MenuMgtApi;
import com.antplatform.admin.api.dto.AuthorityDTO;
import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.request.AuthoritySpec;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.biz.system.resource.AuthorityBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:52:53
 * @description:
 */
@Slf4j
@Service("AuthorityBiz")
public class AuthorityBizImpl implements AuthorityBiz {

    @Autowired
    private AuthorityMgtApi authorityMgtApi;

    /**
     * 查询所有权限
     *
     * @return
     */
    @Override
    public Response<List<AuthorityDTO>> queryAuthorities() {
        try {
            return authorityMgtApi.findBySpec();
        } catch (Exception e) {
            log.error(String.format("invoke authorityMgtApi.findBySpec exception, spec = %s", null), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    @Override
    public Response<Collection<AuthorityDTO>> queryRoleAuth(int roleId) {
        try {
            return authorityMgtApi.findBySpec(roleId);
        } catch (Exception e) {
            log.error(String.format("invoke authorityMgtApi.findBySpec exception, spec = %s", roleId), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 保存权限信息
     *
     * @param authoritySpec
     * @return
     */
    @Override
    public Response<Boolean> save(AuthoritySpec authoritySpec) {
        try {
            return authorityMgtApi.save(authoritySpec);
        } catch (Exception e) {
            log.error(String.format("invoke authorityMgtApi.save exception, spec = %s", authoritySpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 删除权限信息
     *
     * @param authoritySpec
     * @return
     */
    @Override
    public Response<Boolean> delete(AuthoritySpec authoritySpec) {
        try {
            return authorityMgtApi.delete(authoritySpec);
        } catch (Exception e) {
            log.error(String.format("invoke authorityMgtApi.delete exception, spec = %s", authoritySpec), e);
            return Responses.requestTimeout();
        }
    }
}
