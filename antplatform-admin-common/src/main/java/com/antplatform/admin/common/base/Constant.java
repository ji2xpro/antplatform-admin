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
    public static Set<String> IGNORE_PATH_SET = new HashSet<>();

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

    /**
     * 过期时间
     */
    public static class ExpireTime {
        private ExpireTime() {
        }
        public static final int TEN_SEC =  10;//10s
        public static final int THIRTY_SEC =  30;//30s
        public static final int ONE_MINUTE =  60;//一分钟
        public static final int THIRTY_MINUTES =  60 * 30;//30分钟
        public static final int ONE_HOUR = 60 * 60;//一小时
        public static final int THREE_HOURS = 60 * 60 * 3;//三小时
        public static final int TWELVE_HOURS =  60 * 60 * 12;//十二小时，单位s
        public static final int ONE_DAY = 60 * 60 * 24;//二十四小时
    }
}
