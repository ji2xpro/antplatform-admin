package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Organization;
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
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {

    @Override
    OrganizationDTO toDto(Organization organization);


    @Override
    Collection<OrganizationDTO> toDto(Collection<Organization> entityList);

    @Mappings({
            @Mapping(source = "organizationSpec.organizationId", target = "id")
    })
    Organization commandToEntity(OrganizationSpec organizationSpec);

}