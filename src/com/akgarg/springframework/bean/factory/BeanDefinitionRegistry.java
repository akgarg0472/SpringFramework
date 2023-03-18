package com.akgarg.springframework.bean.factory;

import com.akgarg.springframework.bean.factory.BeanDefinition;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void unregisterBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    Object register(Class<?> clazz);

}
