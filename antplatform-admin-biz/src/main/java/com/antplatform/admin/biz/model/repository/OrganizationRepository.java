package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.OrganizationMapper;
import com.antplatform.admin.biz.model.Organization;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.common.dto.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:11:20
 * @description:
 */
@Component
public class OrganizationRepository {
    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 分页查询组织列表
     * @param spec
     * @return
     */
    public PageModel findPageBySpec(OrganizationPageSpec spec) {
        PageModel pageModel = new PageModel(spec.getPageNo(), spec.getPageSize());
        Sqls sqls = Sqls.custom();

        if (StringUtils.isNotEmpty(spec.getName())) {
            sqls.andLike("name", "%" + spec.getName() + "%");
        }
        if (StringUtils.isNotEmpty(spec.getKeypoint())) {
            sqls.andEqualTo("keypoint", spec.getKeypoint());
        }

        sqls.andEqualTo("isDelete", IsDeleteStatus.EXITS.getCode());

        Example example = Example.builder(Organization.class).where(sqls).orderByDesc("id").orderByDesc("createTime").build();

        organizationMapper.selectByExampleAndPageModel(pageModel, example);
        return pageModel;
    }

    /**
     * 查询组织信息
     *
     * @param organizationSpec
     * @return
     */
    public Organization findBySpec(OrganizationSpec organizationSpec) {
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();

        if (organizationSpec.getOrganizationId() != null) {
            criteria.andNotEqualTo("id", organizationSpec.getOrganizationId());
        }
        if (organizationSpec.getName() != null) {
            criteria.andEqualTo("name", organizationSpec.getName());
        }
        if (organizationSpec.getKeypoint() != null) {
            criteria.andEqualTo("keypoint", organizationSpec.getKeypoint());
        }

        List<Organization> organizations = organizationMapper.selectByExample(example);
        return CollectionUtils.isEmpty(organizations) ? null : organizations.get(0);
    }

    /**
     * 新增或更新组织信息
     *
     * @param organization
     * @return
     */
    public Boolean createOrUpdate(Organization organization) {
        Organization oldOrganization = organizationMapper.selectByPrimaryKey(organization.getId());
        int index;
        if (oldOrganization == null) {
            index = organizationMapper.insertSelective(organization);
        } else {
            index = organizationMapper.updateByPrimaryKeySelective(organization);
        }
        return index != 0;
    }
}
