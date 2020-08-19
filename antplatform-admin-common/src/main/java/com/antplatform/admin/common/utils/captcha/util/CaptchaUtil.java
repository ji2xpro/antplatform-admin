package com.antplatform.admin.common.utils.captcha.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: maoyan
 * @date: 2020/7/16 17:47:13
 * @description:
 */
public class CaptchaUtil {
    public static boolean isVerified(String verCode,String verKey,  HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        String cacheVerCode = (String) servletContext.getAttribute("code_" + verKey);
        return verCode.equalsIgnoreCase(cacheVerCode);
    }
}
