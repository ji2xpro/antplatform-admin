package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.infrastructure.shiro.account.UserContext;
import com.antplatform.admin.biz.mapper.MenuMapper;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.common.dto.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:54:56
 * @description:
 */
@Component
public class MenuRepository {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 新增或更新菜单信息
     *
     * @param menu
     * @return
     */
    public Boolean createOrUpdate(Menu menu) {
        int index;
        if (menu.getId() != null) {
            menu.setEditor(UserContext.getCurrentUser().getAccount());
            index = menuMapper.updateByPrimaryKeySelective(menu);
        } else {
            // 新增时需手动拼接pathID
            if (menu.getParentId() != null && menu.getParentId() > 0){
                Menu parentMenu = menuMapper.selectByPrimaryKey(menu.getParentId());
                if (parentMenu != null){
                    menu.setPathId(parentMenu.getPathId() + "-" + parentMenu.getId());
                }
            }else {
                menu.setPathId("0");
            }

            menu.setCreator(UserContext.getCurrentUser().getAccount());
            index = menuMapper.insertSelective(menu);
        }
        return index != 0;
    }
}
