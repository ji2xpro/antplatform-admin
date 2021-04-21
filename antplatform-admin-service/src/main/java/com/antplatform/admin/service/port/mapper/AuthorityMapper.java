package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.AuthorityDTO;
import com.antplatform.admin.api.request.AuthoritySpec;
import com.antplatform.admin.biz.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/7/9 13:52:10
 * @description:
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {

    @Override
    AuthorityDTO toDto(Authority authority);

    Authority commandToEntity(AuthoritySpec authoritySpec);

    @Override
    Collection<AuthorityDTO> toDto(Collection<Authority> entityList);
}