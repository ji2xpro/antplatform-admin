package com.antplatform.admin.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: maoyan
 * @date: 2021/4/22 20:14:23
 * @description:
 */
public class AntplatformAdminWebServletInitializer extends SpringBootServletInitializer {
    /**
     * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里一定要指向原先用main方法执行的Application启动类
        return builder.sources(AntplatformAdminWebApplication.class);
    }
}
