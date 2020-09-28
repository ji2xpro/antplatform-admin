package com.antplatform.admin.service;

import com.antplatform.admin.api.LoginMgtApi;
import com.antplatform.admin.api.dto.LoginDTO;
import com.antplatform.admin.api.enums.UserStatus;
import com.antplatform.admin.api.request.UserMgtSpec;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.infrastructure.shiro.jwt.JWTFilter;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.biz.service.UserService;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.base.component.JwtComponent;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.common.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author: maoyan
 * @date: 2020/7/9 17:09:33
 * @description:
 */
@Slf4j
@Service("LoginMgtApi")
public class LoginMgtPortService implements LoginMgtApi {
    @Autowired
    private UserService userService;


    @Autowired
    private JwtComponent jwtComponent;

    /**
     * 登录
     *
     * @param userSpec
     * @return
     */
    @Override
    public Response<LoginDTO> submitLogin(UserSpec userSpec) {

        User user = userService.findBySpec(userSpec);

        if (StringUtils.isEmpty(user)){
            return Responses.fail(ResponseCode.INVALID_USERNAME_PASSWORD);
        }
        if (user.getStatus() == UserStatus.DISABLE.getCode()){
            return Responses.fail(ResponseCode.USER_LOCKED);
        }

        LoginDTO loginDTO = new LoginDTO();
        String token = JWTUtil.sign(user.getUsername(), user.getPassword(), Constant.ExpTimeType.WEB);
        loginDTO.setToken(token);

        return Responses.of(loginDTO);


//        // 将用户名和密码封装到UsernamePasswordToken
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userSpec.getUsername(), userSpec.getPassword());
//        // 获取Subject实例对象，用户实例
//        Subject currentUser = SecurityUtils.getSubject();
//        try {
//            /**
//             * 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
//             * 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
//             * 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
//             *
//             * {@link MyRealm#doGetAuthenticationInfo(AuthenticationToken)}
//             */
//            currentUser.login(usernamePasswordToken);
//
//            LoginDTO loginDTO = new LoginDTO();
//            loginDTO.setToken("admin-token");
//
//            return Responses.of(loginDTO);
//        } catch (UnknownAccountException e) {
//            log.error("账户不存在！", e);
//            return Responses.fail(ResponseCode.BUSINESS_ERROR.getCode(),e.getMessage());
//        } catch (LockedAccountException e){
//            log.error("帐号已被锁定，禁止登录！", e);
//            return Responses.fail(ResponseCode.BUSINESS_ERROR.getCode(),e.getMessage());
//        } catch (AuthenticationException e) {
//            log.error("用户名或密码输入错误！", e);
//            return Responses.fail(ResponseCode.BUSINESS_ERROR.getCode(),"用户名或密码输入错误！");
//        }
    }

    /**
     * 登出
     *
     * @return
     */
    @Override
    public Response<Boolean> submitLogout() {
        return null;
    }

    /**
     * 校验username是否存在
     *
     * @param username
     * @return
     */
    @Override
    public Response<Object> checkName(String username) {
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(username);
        User user = userService.findBySpec(userSpec);

        if (user == null){
            return Responses.of(null,"");
        }
        return Responses.of(null,"该用户已存在");
    }

    /**
     * 注册
     *
     * @param userMgtSpec
     * @return
     */
    @Override
    public Response<String> register(UserMgtSpec userMgtSpec) {


        return null;
    }
}
