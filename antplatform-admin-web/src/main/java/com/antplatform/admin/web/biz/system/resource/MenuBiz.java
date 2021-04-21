package com.antplatform.admin.web.biz.system.resource;

import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.Response;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:46:56
 * @description:
 */
public interface MenuBiz {

    /**
     * 查询所有菜单
     *
     * @return
     */
    Response<List<MenuDTO>> queryMenus();

    /**
     * 保存菜单信息
     *
     * @param menuSpec
     * @return
     */
    Response<Boolean> save(MenuSpec menuSpec);

    /**
     * 删除菜单信息
     *
     * @param menuSpec
     * @return
     */
    Response<Boolean> delete(MenuSpec menuSpec);
}
