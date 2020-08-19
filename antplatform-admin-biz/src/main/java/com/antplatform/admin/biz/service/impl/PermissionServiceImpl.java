package com.antplatform.admin.biz.service.impl;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.biz.mapper.PermissionMapper;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/11 11:09:38
 * @description:
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 获取权限列表
     *
     * @param permissionListSpec
     * @return
     */
    @Override
    public Collection<Permission> findBySpec(PermissionListSpec permissionListSpec) {
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        if (permissionListSpec.getParentId() > 0){
            criteria.andEqualTo("parentId",permissionListSpec.getParentId());
        }
//        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<Permission> permissions = permissionMapper.selectByExample(example);
        return permissions;
    }
}
