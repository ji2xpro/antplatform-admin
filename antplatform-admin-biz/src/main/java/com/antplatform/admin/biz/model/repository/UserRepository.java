package com.antplatform.admin.biz.model.repository;

import com.antplatform.admin.api.enums.IsDeleteStatus;
import com.antplatform.admin.api.enums.UserStatus;
import com.antplatform.admin.api.request.RolePageSpec;
import com.antplatform.admin.api.request.UserPageSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.mapper.UserMapper;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.common.dto.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

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
//        if (StringUtils.isNotBlank(userSpec.getPassword())){
//            criteria.andEqualTo("password",userSpec.getPassword());
//        }
//        criteria.andEqualTo("status",UserStatus.NORMAL.getCode());
//        criteria.andEqualTo("isDelete",IsDeleteStatus.EXITS.getCode());

        List<User> users = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }

    /**
     * 分页查询用户列表
     *
     * @param spec
     * @return
     */
    public PageModel findPageBySpec(UserPageSpec spec) {
        PageModel pageModel = new PageModel(spec.getPageNo(), spec.getPageSize());
        Sqls sqls = Sqls.custom();
        if (spec.getId() != null && spec.getId() > 0) {
            sqls.andEqualTo("id", spec.getId());
        }
        if (StringUtils.isNotEmpty(spec.getUsername())) {
            sqls.andLike("username", "%" + spec.getUsername() + "%");
        }
        if (StringUtils.isNotEmpty(spec.getMobile())) {
            sqls.andEqualTo("mobile", spec.getMobile());
        }
        if (StringUtils.isNotEmpty(spec.getEmail())) {
            sqls.andEqualTo("email", spec.getEmail());
        }
        if (spec.getRoleId() != null) {
            sqls.andEqualTo("roleId", spec.getRoleId());
        }
        if (spec.getStatus() != null) {
            sqls.andEqualTo("status", spec.getStatus());
        }
        sqls.andEqualTo("isDelete", IsDeleteStatus.EXITS.getCode());

        Example example = Example.builder(User.class).where(sqls).orderByDesc("id").orderByDesc("createTime").build();

        userMapper.selectByExampleAndPageModel(pageModel, example);
        return pageModel;
    }
}
