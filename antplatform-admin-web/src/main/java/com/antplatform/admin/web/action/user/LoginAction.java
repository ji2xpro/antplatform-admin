package com.antplatform.admin.web.action.user;


import com.antplatform.admin.api.dto.UserDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxCode;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.web.biz.user.UserBiz;
import com.antplatform.admin.web.entity.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author: maoyan
 * @date: 2019/10/30 18:19:53
 * @description:
 */
@RestController
public class LoginAction {
    @Autowired
    private UserBiz userBiz;

//    @CrossOrigin
    @PostMapping(value = "/user/login")
    @ResponseBody
    public AjaxResult<UserDTO> login(@RequestBody UserRequest userRequest) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = HtmlUtils.htmlEscape(userRequest.getUsername());
        String password = userRequest.getPassword();

        Response<UserDTO> response = userBiz.queryUser(username,password);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(response.getData());
        }
        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE,response.getMsg());
    }

    @GetMapping(value = "/user/info")
    @ResponseBody
    public AjaxResult<UserDTO> getUserInfo(@RequestParam String token){
        Response<UserDTO> response = userBiz.queryUserRole(token);

        if (response.isSuccess()) {
            return AjaxResult.createSuccessResult(response.getData());
        }
        return AjaxResult.createFailedResult(AjaxCode.ERROR_CODE,"");
    }


    @PostMapping(value = "/user/logout")
    @ResponseBody
    public AjaxResult<String> logout(){
        return AjaxResult.createSuccessResult("success");
    }
}