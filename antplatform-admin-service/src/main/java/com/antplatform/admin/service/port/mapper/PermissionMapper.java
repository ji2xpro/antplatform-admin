package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.User;
import org.apache.shiro.authc.Account;
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
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

    @Override
    PermissionDTO toDto(Permission permission);


    @Override
    Collection<PermissionDTO> toDto(Collection<Permission> entityList);
}