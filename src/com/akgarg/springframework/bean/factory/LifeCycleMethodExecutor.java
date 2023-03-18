package com.akgarg.springframework.bean.factory;

/**
 * @author Akhilesh Garg
 * @since 05-03-2023
 */
public interface LifeCycleMethodExecutor {

    void executeInitMethods(BeanDefinition beanDefinition);

    void executeDestroyMethods(BeanDefinition beanDefinition);

}
