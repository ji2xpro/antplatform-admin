package com.antplatform.admin.biz.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文
 *
 * @author: maoyan
 * @date: 2020/7/28 20:08:02
 * @description:
 */
@Component
public class SpringContextBean implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Get a Spring bean by name
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        T bean = (T) context.getBean(name);
        return bean;
    }

    /**
     * Get a Spring bean by type
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (context != null) {
            try {
                return context.getBean(clazz);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
