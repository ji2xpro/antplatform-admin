package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Organization;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.common.dto.PageModel;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:05:25
 * @description:
 */
public interface OrganizationService {

    /**
     * 分页查询组织列表
     * @param organizationPageSpec
     * @return
     */
    PageModel findPageBySpec(OrganizationPageSpec organizationPageSpec);

    /**
     * 查询组织信息
     *
     * @param organizationSpec
     * @return
     */
    Organization findBySpec(OrganizationSpec organizationSpec);

    /**
     * 更新组织信息
     *
     * @param organization
     * @return
     */
    Boolean update(Organization organization);
}
