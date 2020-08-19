package com.antplatform.admin.common.utils;

import org.springframework.util.DigestUtils;

/**
 * @author: maoyan
 * @date: 2020/7/23 15:04:07
 * @description:
 */
public class Md5Util {

    /**
     * 通过盐值进行md5加密
     *
     * @param password 未加密的密码
     * @param salt     盐值，默认使用用户名就可
     * @return
     */
    public static String encrypt(String password, String salt) {
        return encrypt(password + salt);
    }

    /**
     * md5加密
     *
     * @param password 未加密的密码
     * @return
     */
    public static String encrypt(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456","admin"));
        System.out.println(encrypt(encrypt("123456","admin")));
    }

}
