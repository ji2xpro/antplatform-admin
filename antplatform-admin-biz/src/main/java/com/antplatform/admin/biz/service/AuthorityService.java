package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:04:15
 * @description:
 */
public interface AuthorityService {
    /**
     * 查询所有权限
     *
     * @return
     */
    Collection<Authority> findBySpec();

    /**
     * 查询角色权限
     *
     * @param roleSpec
     * @return
     */
    Collection<Authority> findBySpec(RoleSpec roleSpec);

    /**
     * 查询角色权限关联
     *
     * @param roleId
     * @return
     */
    Collection<RoleAuthority> findBySpec(int roleId);

    /**
     * 保存权限信息
     *
     * @param authority
     * @return
     */
    Boolean save(Authority authority);

    /**
     * 删除菜单信息
     *
     * @param authority
     * @return
     */
    Boolean delete(Authority authority);


}
