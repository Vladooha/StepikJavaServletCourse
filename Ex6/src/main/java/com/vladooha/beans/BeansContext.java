package com.vladooha.beans;

import java.util.HashMap;
import java.util.Map;

public class BeansContext {
    private static BeansContext ourInstance;

    public static BeansContext getInstance() {
        if (ourInstance == null) {
            ourInstance = new BeansContext();
        }

        return ourInstance;
    }

    private Map<Class, Object> beansMap = new HashMap<>();

    private BeansContext() { }

    public boolean put(Class beanClass, Object bean) {
        if (beanClass != null &&
                !beansMap.containsKey(beanClass) &&
                bean != null &&
                beanClass.isAssignableFrom(bean.getClass())) {

            beansMap.put(beanClass, bean);

            return true;
        } else {
            return false;
        }
    }

    public <T> T get(Class<T> beanClass) {
        if (beanClass != null && beansMap.containsKey(beanClass)) {
            return (T)(beansMap.get(beanClass));
        } else {
            return null;
        }
    }

    public <T> T remove(Class<T> beanClass) {
        if (beanClass != null && beansMap.containsKey(beanClass)) {
            return (T)(beansMap.remove(beanClass));
        } else {
            return null;
        }
    }
}
