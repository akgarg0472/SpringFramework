package com.akgarg.springframework.bean.factory;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public interface ConfigurableBeanFactory extends BeanFactory, BeanDefinitionRegistry {

    ConfigurableBeanFactory getBeanFactory();

    BeanResolver getBeanNameResolver();

    BeanDefinitionResolver getBeanDefinitionResolver();

    BeanDefinition registerMethodBeanDefinition(Object instance, Method method);

}
