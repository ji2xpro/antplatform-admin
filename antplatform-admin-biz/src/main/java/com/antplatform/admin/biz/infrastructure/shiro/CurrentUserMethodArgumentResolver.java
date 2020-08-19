package com.antplatform.admin.biz.infrastructure.shiro;

import com.antplatform.admin.biz.model.User;
import com.antplatform.admin.common.annotation.auth.CurrentUser;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定@CurrentUser的方法参数解析器，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 *
 * @author: maoyan
 * @date: 2020/8/13 16:10:55
 * @description:
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

    /**
     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取用户
        User user = (User) webRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
        if (user == null) {
            throw new UnauthorizedException("获取用户信息失败");
        }
        return user;
    }
}
