package com.antplatform.admin.service;

import com.antplatform.admin.api.OrganizationMgtApi;
import com.antplatform.admin.api.RoleMgtApi;
import com.antplatform.admin.api.dto.OrganizationDTO;
import com.antplatform.admin.api.dto.PermissionDTO;
import com.antplatform.admin.api.dto.RoleDTO;
import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.OrganizationPageSpec;
import com.antplatform.admin.api.request.OrganizationSpec;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.model.Organization;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.service.OrganizationService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.common.dto.PageModel;
import com.antplatform.admin.common.dto.PageModelHelper;
import com.antplatform.admin.common.dto.PagedResponse;
import com.antplatform.admin.common.dto.PagedResponses;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.service.port.mapper.OrganizationMapper;
import com.antplatform.admin.service.port.mapper.RoleMapper;
import com.antplatform.admin.service.port.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:47:44
 * @description:
 */
@Service("OrganizationMgtApi")
public class OrganizationMgtPortService implements OrganizationMgtApi {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 分页查询组织列表
     *
     * @param spec
     * @return
     */
    @Override
    public PagedResponse<OrganizationDTO> findPageBySpec(OrganizationPageSpec spec) {
        PageModel pageModel = organizationService.findPageBySpec(spec);

        List<OrganizationDTO> organizationDTOList = (List<OrganizationDTO>) organizationMapper.toDto((List<Organization>) pageModel.getRecords());

        List<OrganizationDTO> menus = UtilHandler.assembleResourceTree(organizationDTOList);

        return PagedResponses.of(menus,
                pageModel.getRecordCount(), PageModelHelper.hasNext(pageModel));
    }

    /**
     * 查询组织信息
     *
     * @param spec
     * @return
     */
    @Override
    public Response<OrganizationDTO> findBySpec(OrganizationSpec spec) {
        Organization organization = organizationService.findBySpec(spec);

        if (organization == null) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "查询组织信息失败");
        }
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        return Responses.of(organizationDTO);
    }

    /**
     * 更新组织信息
     *
     * @param spec
     * @return
     */
    @Override
    public Response<Boolean> updateOrganization(OrganizationSpec spec) {
        Organization organization = organizationMapper.commandToEntity(spec);

        Boolean temp = organizationService.update(organization);
        if (!temp) {
            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "更新组织信息失败");
        }
        return Responses.of(temp);
    }

//    /**
//     * 新增或更新角色信息
//     *
//     * @param spec
//     * @return
//     */
//    @Override
//    public Response<Boolean> saveOrUpdate(RoleSpec spec) {
//        Role role = roleMapper.commandToEntity(spec);
//
//        Boolean temp = roleService.saveOrUpdate(role);
//        if (!temp) {
//            return Responses.fail(ResponseCode.VALIDATION_ERROR.getCode(), "新增或更新角色信息失败");
//        }
//        return Responses.of(temp);
//    }
//
//
//    /**
//     * 删除角色信息
//     *
//     * @param spec
//     * @return
//     */
//    @Override
//    public Response<Boolean> delete(RoleSpec spec) {
//        Role role = roleMapper.commandToEntity(spec);
//        role.setIsDelete(IsDeleteStatus.DELETED.getCode());
//
//        Boolean temp = roleService.saveOrUpdate(role);
//
//        return Responses.of(temp);
//    }


}
