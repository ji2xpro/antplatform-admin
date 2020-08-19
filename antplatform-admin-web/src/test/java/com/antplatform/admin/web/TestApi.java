package com.antplatform.admin.web;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.service.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/12 17:36:51
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApi {

    @Autowired
    PermissionService permissionService;

    @Test
    public void test11(){
        PermissionListSpec permissionListSpec = new PermissionListSpec();
        Collection<Permission> list = permissionService.findBySpec(permissionListSpec);

        System.out.println(list);
    }
}
