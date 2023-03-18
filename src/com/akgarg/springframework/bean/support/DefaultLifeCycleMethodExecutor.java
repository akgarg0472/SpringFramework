package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.LifeCycleMethodExecutor;

/**
 * @author Akhilesh Garg
 * @since 05-03-2023
 */
public class DefaultLifeCycleMethodExecutor implements LifeCycleMethodExecutor {

    private final InitializingBeanProcessor initializingBeanProcessor;
    private final DisposableBeanProcessor disposableBeanProcessor;

    public DefaultLifeCycleMethodExecutor() {
        this.initializingBeanProcessor = new InitializingBeanProcessor();
        this.disposableBeanProcessor = new DisposableBeanProcessor();
    }

    @Override
    public void executeInitMethods(final BeanDefinition beanDefinition) {

    }

    @Override
    public void executeDestroyMethods(final BeanDefinition beanDefinition) {

    }

}
