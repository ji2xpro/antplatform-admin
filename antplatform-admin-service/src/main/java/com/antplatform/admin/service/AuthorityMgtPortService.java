package com.antplatform.admin.service;

import com.antplatform.admin.api.AuthorityMgtApi;
import com.antplatform.admin.api.dto.AuthorityDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.AuthoritySpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Authority;
import com.antplatform.admin.biz.service.AuthorityService;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:50:29
 * @description:
 */
@Service("AuthorityMgtApi")
public class AuthorityMgtPortService implements AuthorityMgtApi {
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    /**
     * 查询所有权限
     *
     * @return
     */
    @Override
    public Response<List<AuthorityDTO>> findBySpec() {
        Collection<Authority> authorities = authorityService.findBySpec();

        Collection<AuthorityDTO> authorityDTOS = authorityMapper.toDto(authorities);

        List<AuthorityDTO> authorityDTOList = UtilHandler.assembleResourceTree(authorityDTOS);

        return Responses.of(authorityDTOList);
    }

    /**
     * 查询角色权限列表
     *
     * @param roleId
     * @return
     */
    @Override
    public Response<Collection<AuthorityDTO>> findBySpec(int roleId) {
        RoleSpec roleSpec = new RoleSpec();
        roleSpec.setRoleId(roleId);

        Collection<Authority> authorities = authorityService.findBySpec(roleSpec);

        Collection<AuthorityDTO> authorityDTOS = authorityMapper.toDto(authorities);

        return Responses.of(authorityDTOS);
    }

    /**
     * 保存权限信息
     *
     * @param authoritySpec
     * @return
     */
    @Override
    public Response<Boolean> save(AuthoritySpec authoritySpec) {
        Authority authority = authorityMapper.commandToEntity(authoritySpec);

        Boolean temp = authorityService.save(authority);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "保存权限信息失败");
        }
        return Responses.of(temp);
    }

    /**
     * 删除权限信息
     *
     * @param authoritySpec
     * @return
     */
    @Override
    public Response<Boolean> delete(AuthoritySpec authoritySpec) {
        Authority authority = authorityMapper.commandToEntity(authoritySpec);
        authority.setIsDelete(IsDeleteStatus.DELETED.getCode());
        Boolean temp = authorityService.delete(authority);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "删除权限信息失败");
        }
        return Responses.of(temp);
    }
}
