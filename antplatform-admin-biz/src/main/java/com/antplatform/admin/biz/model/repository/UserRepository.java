package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.enums.UserStatus;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.mapper.UserMapper;
import com.antplatform.admin.biz.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/8/11 21:44:13
 * @description:
 */
@Component
public class UserRepository {
    @Autowired
    UserMapper userMapper;

    public User findBySpec(UserSpec userSpec){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (userSpec.getUserId() > 0){
            criteria.andEqualTo("id",userSpec.getUserId());
        }
        if (StringUtils.isNotBlank(userSpec.getUsername())){
            criteria.andEqualTo("username",userSpec.getUsername());
        }
        if (StringUtils.isNotBlank(userSpec.getPassword())){
            criteria.andEqualTo("password",userSpec.getPassword());
        }
//        criteria.andEqualTo("status",UserStatus.NORMAL.getCode());
//        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<User> users = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
