package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.biz.model.Organization;
import com.antplatform.admin.biz.model.repository.OrganizationRepository;
import com.antplatform.admin.biz.service.OrganizationService;
import com.antplatform.admin.common.dto.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:08:31
 * @description:
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * 分页查询组织列表
     *
     * @param organizationPageSpec
     * @return
     */
    @Override
    public PageModel findPageBySpec(OrganizationPageSpec organizationPageSpec) {
        return organizationRepository.findPageBySpec(organizationPageSpec);
    }

    /**
     * 查询组织信息
     *
     * @param organizationSpec
     * @return
     */
    @Override
    public Organization findBySpec(OrganizationSpec organizationSpec) {
        return organizationRepository.findBySpec(organizationSpec);
    }

    /**
     * 更新组织信息
     *
     * @param organization
     * @return
     */
    @Override
    public Boolean update(Organization organization) {
        return organizationRepository.createOrUpdate(organization);
    }
}
