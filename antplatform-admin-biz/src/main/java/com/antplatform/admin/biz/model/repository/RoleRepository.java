package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.dto.RolePermissionDTO;
import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.RolePermissionSpec;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.biz.mapper.RoleMapper;
import com.antplatform.admin.biz.mapper.RolePermissionMapper;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.RolePermission;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.common.dto.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:54:56
 * @description:
 */
@Component
public class RoleRepository {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionService permissionService;

    public PageModel findPageBySpec(RolePageSpec spec) {

        Example example1 = new Example(Role.class);
        Example.Criteria criteria = example1.createCriteria();
        criteria.andEqualTo("name","Admin");
        List<Role> roles = roleMapper.selectByExample(example1);

        System.out.println("======"+roles);


        PageModel pageModel = new PageModel(spec.getPageNo(), spec.getPageSize());
        Sqls sqls = Sqls.custom();
        if (spec.getId() != null && spec.getId() > 0) {
            sqls.andEqualTo("id", spec.getId());
        }
        if (StringUtils.isNotEmpty(spec.getName())) {
            sqls.andLike("name", "%" + spec.getName() + "%");
        }
        if (StringUtils.isNotEmpty(spec.getKeypoint())) {
            sqls.andEqualTo("keypoint", spec.getKeypoint());
        }
        if (spec.getStatus() != null) {
            sqls.andEqualTo("status", spec.getStatus());
        }
        sqls.andEqualTo("isDelete", IsDeleteStatus.EXITS.getCode());

        Example example = Example.builder(Role.class).where(sqls).orderByDesc("id").orderByDesc("createTime").build();

        roleMapper.selectByExampleAndPageModel(pageModel, example);
        return pageModel;
    }

    public Role findBySpec(RoleSpec roleSpec){
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();

        if (roleSpec.getRoleId() != null){
            criteria.andNotEqualTo("id",roleSpec.getRoleId());
        }
        if (roleSpec.getName() != null){
            criteria.andEqualTo("name",roleSpec.getName());
        }
        if (roleSpec.getKeypoint() != null){
            criteria.andEqualTo("keypoint",roleSpec.getKeypoint());
        }

        List<Role> roles = roleMapper.selectByExample(example);
        return CollectionUtils.isEmpty(roles) ? null : roles.get(0);
    }

    public Boolean saveOrUpdate(Role role){

        Role oldRole = roleMapper.selectByPrimaryKey(role.getId());
        int index;
        if (oldRole == null){
           index =  roleMapper.insertSelective(role);
        }else {
            index = roleMapper.updateByPrimaryKeySelective(role);
        }



//        Example example = new Example(Role.class);
//        Example.Criteria criteria = example.createCriteria();
//        int index = 0;
//
//        if (role.getId() != null && role.getId() > 0){
//
//            criteria.andEqualTo("id",role.getId());
//
//             index = roleMapper.updateByExampleSelective(role,example);
//        }
        return index != 0 ;
    }

    public Boolean assignPermission(Collection<RolePermission> rolePermissions){
        Map<Boolean,List<RolePermission>> rolePermMap = rolePermissions.stream().collect(Collectors.partitioningBy(rolePermission -> rolePermission.getIsDelete() == IsDeleteStatus.EXITS.getCode()));


        if (!rolePermMap.get(Boolean.TRUE).isEmpty()){
            List<RolePermission> newList = rolePermMap.get(Boolean.TRUE);

            Collection<RolePermission> oldList = permissionService.findBySpec(newList.get(0).getRoleId(),null);

            Map<Integer,RolePermission> rolePermissionMap = oldList.stream().collect(Collectors.toMap(RolePermission::getPermissionId,Function.identity()));

            List<RolePermission> insertList = new ArrayList<>();
            List<RolePermission> updateList = new ArrayList<>();

            for (RolePermission rolePerm: newList) {
                RolePermission rolePermission = rolePermissionMap.get(rolePerm.getPermissionId());
                if (rolePermission  == null){
                    insertList.add(rolePerm);
                }else {
                    rolePerm.setId(rolePermission.getId());
                    updateList.add(rolePerm);
                }
            }

            if (!insertList.isEmpty()){
                rolePermissionMapper.insertList(insertList);

            }
            if (!updateList.isEmpty()){
                rolePermissionMapper.bulkUpdateByExampleSelective(updateList);
            }

//            rolePermissionMapper.insertList(rolePermMap.get(Boolean.TRUE));
        }
        if (!rolePermMap.get(Boolean.FALSE).isEmpty()){
            rolePermissionMapper.bulkUpdateByExampleSelective(rolePermMap.get(Boolean.FALSE));
        }
        return true;
    }
}
