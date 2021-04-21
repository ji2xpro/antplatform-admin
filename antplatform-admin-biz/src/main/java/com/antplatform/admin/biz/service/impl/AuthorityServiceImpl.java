package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.AuthorityMapper;
import com.antplatform.admin.biz.mapper.RoleAuthorityMapper;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.repository.AuthorityRepository;
import com.antplatform.admin.biz.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:32:36a
 * @description:
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityMapper authorityMapper;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    /**
     * 查询所有权限
     *
     * @return
     */
    @Override
    public Collection<Authority> findBySpec() {
        Example example = new Example(Authority.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isDelete", IsDeleteStatus.EXITS.getCode());

        List<Authority> authorities = authorityMapper.selectByExample(example);
        return authorities;
    }

    /**
     * 查询角色权限
     *
     * @param roleSpec
     * @return
     */
    @Override
    public Collection<Authority> findBySpec(RoleSpec roleSpec) {
        Collection<Authority> authorities = authorityRepository.findBySpec(roleSpec);
        return authorities.stream().filter(authority -> authority.getIsDelete() == 0).collect(Collectors.toList());
    }

    /**
     * 查询角色权限关联
     *
     * @param roleId
     * @return
     */
    @Override
    public Collection<RoleAuthority> findBySpec(int roleId) {
        Example example = new Example(RoleAuthority.class);
        Example.Criteria criteria = example.createCriteria();
        if(roleId > 0){
            criteria.andEqualTo("roleId",roleId);
        }
        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectByExample(example);
        return roleAuthorities;
    }

    /**
     * 保存权限信息
     *
     * @param authority
     * @return
     */
    @Override
    public Boolean save(Authority authority) {
        return authorityRepository.createOrUpdate(authority);
    }

    /**
     * 删除菜单信息
     *
     * @param authority
     * @return
     */
    @Override
    public Boolean delete(Authority authority) {
        return authorityRepository.createOrUpdate(authority);
    }
}
