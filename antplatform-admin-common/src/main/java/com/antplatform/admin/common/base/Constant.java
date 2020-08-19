package com.antplatform.admin.common.base;

import java.util.HashSet;
import java.util.Set;

/**
 * 常量类
 *
 * @author: maoyan
 * @date: 2020/7/29 11:30:52
 * @description:
 */
public class Constant {
    /**
     * 忽略鉴权的方法列表
     */
    public static Set<String> METHOD_URL_SET = new HashSet<>();

    /**
     * 数组取值
     */
    public class Number {

        public static final int ZERO = 0;

        public static final int ONE = 1;

        public static final int TWO = 2;

        public static final int THREE = 3;

        public static final int FOUR = 4;

        public static final int FIVE = 5;

        public static final int FIFTEEN = 15;
    }

    /**
     * 超时时间类型
     */
    public class ExpTimeType {

        public static final String WEB = "web";

        public static final String APP = "app";

    }
}
