package com.antplatform.admin.web.entity.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: maoyan
 * @date: 2020/1/21 17:59:57
 * @description:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "LoginRequest对象", description = "用户登录信息")
public class LoginRequest {
    @ApiModelProperty(value = "用户账号", required = true, dataType = "String", notes = "账号长度范围应该在2-32位之间。")
    @NotEmpty(message="账号不能为空")
    @Size(min=2,max=32,message="账号长度不正确")
    String username;

    @ApiModelProperty(value = "用户密码", required = true, dataType = "String")
    @NotEmpty(message="密码不能为空")
    String password;

    @ApiModelProperty(value = "验证码", required = true, dataType = "String")
    @NotEmpty(message="验证码不能为空")
    private String vcode;

    @ApiModelProperty(value = "验证码唯一标识", required = true, dataType = "String")
    @NotEmpty(message="验证码唯一标识不能为空")
    private String verkey;
}
