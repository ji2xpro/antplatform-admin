package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.PermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.infrastructure.shiro.account.UserContext;
import com.antplatform.admin.biz.mapper.AuthorityMapper;
import com.antplatform.admin.biz.mapper.PermissionMapper;
import com.antplatform.admin.biz.mapper.RoleAuthorityMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/20 13:06:08
 * @description:
 */
@Component
public class AuthorityRepository {

    @Autowired
    AuthorityMapper authorityMapper;

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    public Collection<Authority> findBySpec(RoleSpec roleSpec) {
        Example example = new Example(RoleAuthority.class);
        Example.Criteria criteria = example.createCriteria();
        if (roleSpec.getRoleId() != null){
            criteria.andEqualTo("roleId",roleSpec.getRoleId());
        }
        if (!CollectionUtils.isEmpty(roleSpec.getRoleIds())){
            criteria.andIn("roleId",roleSpec.getRoleIds());
        }
        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        return authorityMapper.selectByIdList(roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList()));
    }

    /**
     * 新增或更新权限信息
     *
     * @param authority
     * @return
     */
    public Boolean createOrUpdate(Authority authority) {
        int index;
        if (authority.getId() != null) {
            authority.setEditor(UserContext.getCurrentUser().getAccount());
            index = authorityMapper.updateByPrimaryKeySelective(authority);
        } else {
            // 新增时需手动拼接pathID
            if (authority.getParentId() != null && authority.getParentId() > 0){
                Authority parentAuth = authorityMapper.selectByPrimaryKey(authority.getParentId());
                if (parentAuth != null){
                    authority.setPathId(parentAuth.getPathId() + "-" + parentAuth.getId());
                }
            }else {
                authority.setPathId("0");
            }

            authority.setCreator(UserContext.getCurrentUser().getAccount());
            index = authorityMapper.insertSelective(authority);
        }
        return index != 0;
    }
}


