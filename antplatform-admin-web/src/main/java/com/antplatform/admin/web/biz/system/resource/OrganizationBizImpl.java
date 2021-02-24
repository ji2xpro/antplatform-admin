package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.OrganizationMgtApi;
import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/10/22 16:30:04
 * @description:
 */
@Slf4j
@Service("OrganizationBiz")
public class OrganizationBizImpl implements OrganizationBiz{

    @Autowired
    private OrganizationMgtApi organizationMgtApi;

    /**
     * 分页查询组织列表
     *
     * @param organizationPageSpec
     * @return
     */
    @Override
    public PagedResponse<OrganizationDTO> queryOrganizationPage(OrganizationPageSpec organizationPageSpec) {
        try {
            return organizationMgtApi.findPageBySpec(organizationPageSpec);
        } catch (Exception e) {
            log.error(String.format("invoke organizationMgtApi.findPageBySpec exception, spec = %s", organizationPageSpec), e);
            return PagedResponses.requestTimeout();
        }
    }

    /**
     * 查询组织信息
     *
     * @param organizationSpec
     * @return
     */
    @Override
    public Response<OrganizationDTO> queryOrganization(OrganizationSpec organizationSpec) {
        try {
            return organizationMgtApi.findBySpec(organizationSpec);
        } catch (Exception e) {
            log.error(String.format("invoke organizationMgtApi.findBySpec exception, spec = %s", organizationSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 更新组织信息
     *
     * @param organizationSpec
     * @return
     */
    @Override
    public Response<Boolean> updateOrganization(OrganizationSpec organizationSpec) {
        try {
            return organizationMgtApi.updateOrganization(organizationSpec);
        } catch (Exception e) {
            log.error(String.format("invoke organizationMgtApi.updateOrganization exception, spec = %s", organizationSpec), e);
            return Responses.requestTimeout();
        }
    }
}
