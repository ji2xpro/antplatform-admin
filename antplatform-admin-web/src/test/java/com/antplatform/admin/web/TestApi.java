package com.antplatform.admin.web;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.biz.service.UserService;
import net.bytebuddy.asm.Advice;
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

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

//    @Autowired
//    JedisUtils jedisUtils;

    @Test
    public void test11(){
//        PermissionListSpec permissionListSpec = new PermissionListSpec();
//        Collection<Permission> list = permissionService.findBySpec(permissionListSpec);
//
//        System.out.println(list);

//        UserSpec userSpec = new UserSpec();
//        userSpec.setUsername("admin");
//
//        User user = userService.findBySpec(userSpec);
//
//        if (user != null){
//            userSpec.setUserId(user.getId());
//            Collection<Role> roles = roleService.findBySpec(userSpec);
//            System.out.println(roles);
//        }

//        System.out.println(jedisUtils.getSeqNext("1212",2222));



    }
}
