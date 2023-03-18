package com.akgarg.springframework.bean.factory;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public interface BeanLifeCycleMethodExecutor {

    void executeInitMethod(BeanDefinition beanDefinition);

    void executeDestroyMethod(final BeanDefinition beanDefinition);

}
