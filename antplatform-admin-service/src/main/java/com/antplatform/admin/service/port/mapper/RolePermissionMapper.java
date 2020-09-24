package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
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
public interface RolePermissionMapper extends EntityMapper<RolePermissionDTO, RolePermission> {

    @Override
    Collection<RolePermissionDTO> toDto(Collection<RolePermission> entityList);


    Collection<RolePermission> commandToEntity(Collection<RolePermissionDTO> rolePermissionDTOS);
}