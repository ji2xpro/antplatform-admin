package com.antplatform.admin.api;

import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.common.dto.Response;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:49:50
 * @description:
 */
public interface MenuMgtApi {
    /**
     * 查询所有菜单
     * @return
     */
    Response<List<MenuDTO>> findBySpec();

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
