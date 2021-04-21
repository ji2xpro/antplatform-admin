package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.MenuMapper;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.repository.AuthorityRepository;
import com.antplatform.admin.biz.model.repository.MenuRepository;
import com.antplatform.admin.biz.service.AuthorityService;
import com.antplatform.admin.biz.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:32:36
 * @description:
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    AuthorityService authorityService;

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public Collection<Menu> findBySpec() {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    }

    /**
     * 保存菜单信息
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean save(Menu menu) {
        return menuRepository.createOrUpdate(menu);
    }

    /**
     * 删除菜单信息
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean delete(Menu menu) {
        return menuRepository.createOrUpdate(menu);
    }
}
