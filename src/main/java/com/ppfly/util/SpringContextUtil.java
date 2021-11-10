package com.ppfly.util;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringContextUtil {

    public static ApplicationContext applicationContext;

    private SpringContextUtil() {
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 通过beanName获取 Bean.
     *
     * @param beanName
     * @return
     */
    public static <T> T getBean(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return (T) applicationContext.getBean(beanName);
        } else {
            return null;
        }
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过class获取Bean集合
     */
    public static <T> List<T> getBeansByType(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        if (null != map) {
            result.addAll(map.values());
        }
        return result;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

}