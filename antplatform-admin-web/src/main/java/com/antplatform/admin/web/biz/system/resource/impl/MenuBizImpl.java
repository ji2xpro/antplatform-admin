package com.antplatform.admin.web.biz.system.resource.impl;

import com.antplatform.admin.api.MenuMgtApi;
import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.biz.mapper.MenuMapper;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.web.biz.system.resource.MenuBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 19:52:53
 * @description:
 */
@Slf4j
@Service("MenuBiz")
public class MenuBizImpl implements MenuBiz {

    @Autowired
    private MenuMgtApi menuMgtApi;

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public Response<List<MenuDTO>> queryMenus() {
        try {
            return menuMgtApi.findBySpec();
        } catch (Exception e) {
            log.error(String.format("invoke menuMgtApi.findBySpec exception, spec = %s", null), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 保存菜单信息
     *
     * @param menuSpec
     * @return
     */
    @Override
    public Response<Boolean> save(MenuSpec menuSpec) {
        try {
            return menuMgtApi.save(menuSpec);
        } catch (Exception e) {
            log.error(String.format("invoke menuMgtApi.save exception, spec = %s", menuSpec), e);
            return Responses.requestTimeout();
        }
    }

    /**
     * 删除菜单信息
     *
     * @param menuSpec
     * @return
     */
    @Override
    public Response<Boolean> delete(MenuSpec menuSpec) {
        try {
            return menuMgtApi.delete(menuSpec);
        } catch (Exception e) {
            log.error(String.format("invoke menuMgtApi.delete exception, spec = %s", menuSpec), e);
            return Responses.requestTimeout();
        }
    }
}
