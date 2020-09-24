package com.antplatform.admin.common.tracker;

/**
 * @author: maoyan
 * @date: 2020/9/1 16:20:18
 * @description:
 */
/**
 * InitialInstanceInheritableThreadLocal
 * @author danson.liu
 * Not to use <code>InheritableThreadLocal</code> class, there is something wrong in this context
 */
public class InitialInstanceThreadLocal<T> extends ThreadLocal<T> {

    private final Class<T> clazz;

    public InitialInstanceThreadLocal(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected T initialValue() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
