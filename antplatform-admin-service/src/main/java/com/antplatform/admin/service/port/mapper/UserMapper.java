package com.antplatform.admin.service.port.mapper;

import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.biz.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author: maoyan
 * @date: 2020/7/9 13:52:10
 * @description:
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User>{

    @Override
    UserDTO toDto(User user);

}