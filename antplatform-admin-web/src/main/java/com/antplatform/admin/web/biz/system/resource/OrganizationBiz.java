package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/10/22 15:06:21
 * @description:
 */
public interface OrganizationBiz {
    /**
     * 分页查询组织列表
     *
     * @param organizationPageSpec
     * @return
     */
    PagedResponse<OrganizationDTO> queryOrganizationPage(OrganizationPageSpec organizationPageSpec);

    /**
     * 查询组织信息
     *
     * @param organizationSpec
     * @return
     */
    Response<OrganizationDTO> queryOrganization(OrganizationSpec organizationSpec);

    /**
     * 更新组织信息
     *
     * @param organizationSpec
     * @return
     */
    Response<Boolean> updateOrganization(OrganizationSpec organizationSpec);
}
