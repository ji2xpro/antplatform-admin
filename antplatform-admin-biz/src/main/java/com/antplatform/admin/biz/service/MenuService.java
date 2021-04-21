package com.antplatform.admin.biz.service;

import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Role;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:04:15
 * @description:
 */
public interface MenuService {
    /**
     * 查询所有菜单
     *
     * @return
     */
    Collection<Menu> findBySpec();

    /**
     * 保存菜单信息
     *
     * @param menu
     * @return
     */
    Boolean save(Menu menu);

    /**
     * 删除菜单信息
     *
     * @param menu
     * @return
     */
    Boolean delete(Menu menu);


}
