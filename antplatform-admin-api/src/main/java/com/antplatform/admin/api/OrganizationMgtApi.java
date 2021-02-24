package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.Response;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:16:33
 * @description:
 */
public interface OrganizationMgtApi {
    /**
     * 分页查询组织列表
     *
     * @param spec
     * @return
     */
    PagedResponse<OrganizationDTO> findPageBySpec(OrganizationPageSpec spec);

    /**
     * 查询组织信息
     *
     * @param spec
     * @return
     */
    Response<OrganizationDTO> findBySpec(OrganizationSpec spec);

    /**
     * 更新组织信息
     *
     * @param spec
     * @return
     */
    Response<Boolean> updateOrganization(OrganizationSpec spec);
}
