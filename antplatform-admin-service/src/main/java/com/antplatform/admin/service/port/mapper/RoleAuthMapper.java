package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.RoleAuthDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RoleAuthority;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.User;
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
public interface RoleAuthMapper extends EntityMapper<RoleAuthDTO, RoleAuthority> {

    @Override
    @Mappings({
            @Mapping(source = "roleAuthority.authorityId", target = "authId")
    })
    RoleAuthDTO toDto(RoleAuthority roleAuthority);

    @Override
    Collection<RoleAuthDTO> toDto(Collection<RoleAuthority> entityList);

    @Override
    @Mappings({
            @Mapping(source = "roleAuthDTO.authId", target = "authorityId")
    })
    RoleAuthority toEntity(RoleAuthDTO roleAuthDTO);

    Collection<RoleAuthority> commandToEntity(Collection<RoleAuthDTO> roleAuthDTOS);
}