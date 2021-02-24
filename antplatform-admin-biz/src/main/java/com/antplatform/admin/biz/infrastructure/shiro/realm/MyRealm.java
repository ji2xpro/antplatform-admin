package com.antplatform.admin.biz.infrastructure.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antplatform.admin.api.enums.UserStatus;
import com.antplatform.admin.api.request.RoleSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.infrastructure.SpringContextBean;
import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtToken;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.model.Role;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.biz.service.RoleService;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义 MyRealm, 重写认证、授操作<br>
 * 密码输入错误的状态下重试次数的匹配管理
 *
 * @author: maoyan
 * @date: 2020/7/8 14:16:25
 * @description:
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 使用JWT替代原生Token,必须重写此方法,不然Shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        /**
         * 表示此Realm只支持JWTToken类型
         */
        return token instanceof JwtToken;
    }

    /**
     * 身份认证<br>
     * <p>
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *
     * @param authenticationToken
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始进行身份认证......");

        if (null == userService) {
            this.userService = SpringContextBean.getBean(UserService.class);
        }
        String token = (String) authenticationToken.getCredentials();

        // 解密token获得username，用于和数据库进行对比
        String account = JwtUtil.getClaim(token);
        if (account == null) {
            log.error("token无效(空''或者null都不行!)");
            throw new UnknownAccountException("token无效");
        }
        // 通过username从数据库中查找User对象.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(account);
        User user = userService.findBySpec(userSpec);
        if (user == null) {
            log.error("账号不存在!");
            throw new UnknownAccountException("账号不存在!");
        }

        if (user.getStatus() != null && UserStatus.DISABLE.getCode() == user.getStatus()) {
            log.error("帐号已被锁定，禁止登录！");
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }

//        if (!JwtUtil.verify(token, account, user.getPassword())) {
//            log.error("用户名或密码错误(token无效或者与登录者不匹配)!)");
//            throw new UnauthorizedException("用户名或密码错误(token无效或者与登录者不匹配)!");
//        }
        String userString = JSONObject.toJSONString(user);

        // 如果身份认证验证成功，返回一个 AuthenticationInfo 实现；
        return new SimpleAuthenticationInfo(token, user.getPassword(), this.getName());

//        //获取用户的输入的账号
//        String userName = (String) authenticationToken.getPrincipal();
//
//        // 通过username从数据库中查找User对象.
//        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        User user = userService.queryByUsername(userName);
//        if (Objects.isNull(user)) {
//            throw new UnknownAccountException("账号不存在！");
//        }
//
//        if (user.getStatus() != null && UserStatus.DISABLE.getCode() == user.getStatus()) {
//            throw new LockedAccountException("帐号已被锁定，禁止登录！");
//        }
//
//        // 如果身份认证验证成功，返回一个 AuthenticationInfo 实现；
//        return new SimpleAuthenticationInfo(
//                // 这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
//                user,
//                // 密码
//                user.getPassword(),
//                // salt = username + salt
//                ByteSource.Util.bytes(userName),
//                // realm name
//                getName()
//        );
    }

    /**
     * 授权认证<br>
     * <p>
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.warn("开始进行授权操作.......");
        /**
         * 查询用户权限
         * 如果身份认证候传了User对象，则这里能取到user对象
         * 也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
         * 这里传入了token，可以获取到userName
         */
        String account = JwtUtil.getClaim(principalCollection.toString());
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(account);
        User user = userService.findBySpec(userSpec);

        if (user == null) {
            log.error("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        int userId = user.getId();

        // 查询角色
        ArrayList<String> roles = new ArrayList<>();
        // 查询权限
        ArrayList<String> permissions = new ArrayList<>();
        // 查询用户权限
//        UserSpec userSpec = new UserSpec();
        userSpec.setUserId(userId);
        Collection<Role> roleList = roleService.findBySpec(userSpec);
        // 查询用户所在组织权限(暂不启用)
        // List<Role> orgRoleList = organizationRoleService.queryRolesByOrganizationId( );
        for (Role role : roleList) {
            if (!StringUtils.isEmpty(role.getKeypoint())) {
                if (!StringUtils.isEmpty(role.getKeypoint().replace(" ", ""))) {
                    roles.add(role.getKeypoint());
                }
            }
            RoleSpec roleSpec = new RoleSpec();
            roleSpec.setRoleId(role.getId());
            Collection<Permission> permissionList = permissionService.findBySpec(roleSpec);
            if (!CollectionUtils.isEmpty(permissionList)) {
                for (Permission perm : permissionList) {
                    if (!StringUtils.isEmpty(perm.getKeypoint())) {
                        if (!StringUtils.isEmpty(perm.getKeypoint().replace(" ", ""))) {
                            permissions.add(perm.getKeypoint());
                        }
                    }
                }
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Set<String> rolesSet = new HashSet<>(roles);
        simpleAuthorizationInfo.addRoles(rolesSet);
        Set<String> permission = new HashSet<>(permissions);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;


//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        /**
//         * 查询用户角色
//         * 如果身份认证的时候没有传入User对象，这里只能取到userName
//         * 也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
//         */
//        User user = (User) principalCollection.getPrimaryPrincipal();
//
//        if (user == null) {
//            log.error("用户不存在");
//            throw new UnknownAccountException("用户不存在");
//        }
//
//        // 查询用户角色，一个用户可能有多个角色
//        List<Role> roles = iRoleService.getUserRoles(user.getUserId());
//
//        for (Role role : roles) {
//            authorizationInfo.addRole(role.getRole());
//            // 根据角色查询权限
//            List<Permission> permissions = iPermissionService.getRolePermissions(role.getRoleId());
//            for (Permission p : permissions) {
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
//        return authorizationInfo;
    }
}
