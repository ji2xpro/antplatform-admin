package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.UserDTO;
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
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Override
    @Mappings({
            @Mapping(source = "user.username", target = "name")
    })
    UserDTO toDto(User user);

    @Override
    Collection<UserDTO> toDto(Collection<User> entityList);

}