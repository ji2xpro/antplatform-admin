package com.antplatform.admin.biz.infrastructure.shiro.realm;

import com.antplatform.admin.api.enums.UserStatus;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.infrastructure.SpringContextBean;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.biz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * UserRealm只用于登陆验证,故继承AuthenticatingRealm就好了
 *
 * @author: maoyan
 * @date: 2020/7/8 14:16:25
 * @description:
 */
@Slf4j
public class UserRealm extends AuthenticatingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 该方法用于多Realm认证时识别需要使用哪一个Realm
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        /**
         * 表示此Realm只支持UsernamePasswordToken类型
         */
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 身份认证<br>
     * <p>
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *
     * @param token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("开始进行身份认证......");

        if (null == userService) {
            this.userService = SpringContextBean.getBean(UserService.class);
        }
        //获取用户的输入的账号
        String userName = (String) token.getPrincipal();

        // 通过username从数据库中查找User对象.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(userName);
        User user = userService.findBySpec(userSpec);
        if (Objects.isNull(user)) {
            log.error("账号不存在!");
            throw new UnknownAccountException("账号不存在!");
        }

        if (user.getStatus() != null && UserStatus.DISABLE.getCode() == user.getStatus()) {
            log.error("帐号已被锁定，禁止登录！");
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }

        // 如果身份认证验证成功，返回一个 AuthenticationInfo 实现；
        return new SimpleAuthenticationInfo(
                // 这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user,
                // 密码
                user.getPassword(),
                // salt = username + salt
                ByteSource.Util.bytes(userName),
                // realm name
                getName()
        );
    }

//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        return null;
//    }
}
