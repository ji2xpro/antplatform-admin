package com.antplatform.admin.web.action.basic;

import com.antplatform.admin.api.dto.LoginDTO;
import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.api.request.UserSpec;
import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.common.annotation.auth.CurrentUser;
import com.antplatform.admin.common.annotation.auth.NoAuthentication;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.enums.DataType;
import com.antplatform.admin.common.enums.ParamType;
import com.antplatform.admin.common.enums.ResponseCode;
import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.captcha.GifCaptcha;
import com.antplatform.admin.common.utils.captcha.util.CaptchaUtil;
import com.antplatform.admin.web.biz.user.LoginBiz;
import com.antplatform.admin.web.biz.user.UserBiz;
import com.antplatform.admin.web.entity.user.UserRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: maoyan
 * @date: 2019/10/30 18:19:53
 * @description:
 */
@RestController
public class LoginAction {
    @Autowired
    private UserBiz userBiz;

    @Autowired
    private LoginBiz loginBiz;

    @GetMapping("/vcode")
    @NoAuthentication
    @ApiOperation(value = "获取登录时的验证码")
    @ApiImplicitParam(name = "codeKey", value = "验证码唯一标识", dataType = DataType.STRING, paramType = ParamType.QUERY, required = true)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeKey = request.getParameter("codeKey");
        if (codeKey != null && !codeKey.trim().isEmpty()) {
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            GifCaptcha captcha = new GifCaptcha(130, 38, 2);
            ServletContext servletContext = request.getSession().getServletContext();
            servletContext.setAttribute("code_" + codeKey, captcha.text().toLowerCase());
            captcha.out(response.getOutputStream());
        }
    }

    @PostMapping(value = "/user/login")
    @NoAuthentication
    @ApiOperation(value = "执行登录", notes = "返回token")
    public AjaxResult<LoginDTO> login(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = HtmlUtils.htmlEscape(userRequest.getUsername());
        String password = userRequest.getPassword();
        String vcode = userRequest.getVcode();
        String verKey = userRequest.getVerkey();

        if (!CaptchaUtil.isVerified(vcode, verKey, request)) {
            return AjaxResult.createFailedResult(ResponseCode.INVALID_RE_VCODE);
        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return AjaxResult.createFailedResult(ResponseCode.ERROR);
        }
//        Response<UserDTO> response = userBiz.queryUser(username,password);

        UserSpec userSpec = new UserSpec();
        userSpec.setUsername(username);
        Response<LoginDTO> response = loginBiz.submitLogin(userSpec);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(response.getData());
        }
        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE, response.getMsg());
    }

    @GetMapping(value = "/user/info")
    @RequiresAuthentication
    @ApiOperation(value = "登录后获取用户个人信息")
//    public AjaxResult<UserDTO> getUserInfo(@RequestParam String token){
//        Response<UserDTO> response = userBiz.queryUserRole(token);
//
//        if (response.isSuccess()) {
//            return AjaxResult.createSuccessResult(response.getData());
//        }
//        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE,"");
//    }

    public AjaxResult<UserDTO> getUserInfo(@ApiIgnore @CurrentUser User user) {
        int userId = user.getId();
        Response<UserDTO> response = userBiz.queryUserInfo(userId);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(response.getData());
        }
        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE, response.getMsg());
    }


    @PostMapping(value = "/user/logout")
    @NoAuthentication
    @ApiOperation(value = "退出登录")
    public AjaxResult<String> logout() {
        return AjaxResult.createSuccessResult("success");
    }


    @GetMapping(value = "/checkName")
    @NoAuthentication
    public AjaxResult<Object> checkName(@RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE, "参数错误");
        }

        Response<Object> response = loginBiz.checkName(name);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(response.getData(), response.getMsg());
        }
        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE, response.getMsg());
    }

    @PostMapping(value = "/user/register")
    @NoAuthentication
    public AjaxResult<String> register(@RequestBody UserRequest userRequest) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = HtmlUtils.htmlEscape(userRequest.getUsername());
        String password = userRequest.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE, "参数错误");
        }

        return null;

    }
}