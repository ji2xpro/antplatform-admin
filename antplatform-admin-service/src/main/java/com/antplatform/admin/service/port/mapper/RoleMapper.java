package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Role;
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
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

    @Override
    RoleDTO toDto(Role role);


    @Mappings({
            @Mapping(source = "roleSpec.roleId", target = "id")
    })
    Role commandToEntity(RoleSpec roleSpec);


    @Override
    Collection<RoleDTO> toDto(Collection<Role> entityList);

}