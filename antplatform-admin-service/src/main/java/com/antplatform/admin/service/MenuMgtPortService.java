package com.antplatform.admin.service;

import com.antplatform.admin.api.MenuMgtApi;
import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.service.MenuService;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:50:29
 * @description:
 */
@Service("MenuMgtApi")
public class MenuMgtPortService implements MenuMgtApi {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public Response<List<MenuDTO>> findBySpec() {
        Collection<Menu> menus = menuService.findBySpec();

        Collection<MenuDTO> menuDTOS = menuMapper.toDto(menus);

        List<MenuDTO> menuDTOList = UtilHandler.assembleResourceTree(menuDTOS);

        return Responses.of(menuDTOList);
    }

    /**
     * 保存菜单信息
     *
     * @param menuSpec
     * @return
     */
    @Override
    public Response<Boolean> save(MenuSpec menuSpec) {
        Menu menu = menuMapper.commandToEntity(menuSpec);

        Boolean temp = menuService.save(menu);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "保存菜单信息失败");
        }
        return Responses.of(temp);
    }

    /**
     * 删除菜单信息
     *
     * @param menuSpec
     * @return
     */
    @Override
    public Response<Boolean> delete(MenuSpec menuSpec) {
        Menu menu = menuMapper.commandToEntity(menuSpec);
        menu.setIsDelete(IsDeleteStatus.DELETED.getCode());
        Boolean temp = menuService.delete(menu);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "删除菜单信息失败");
        }
        return Responses.of(temp);
    }
}
