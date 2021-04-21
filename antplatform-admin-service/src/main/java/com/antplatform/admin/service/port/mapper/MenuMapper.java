package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.MenuDTO;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.request.MenuSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Menu;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/7/9 13:52:10
 * @description:
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    @Override
    MenuDTO toDto(Menu menu);

    Menu commandToEntity(MenuSpec menuSpec);

    @Override
    Collection<MenuDTO> toDto(Collection<Menu> entityList);
}