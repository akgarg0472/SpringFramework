package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanLifeCycleMethodExecutor;
import com.akgarg.springframework.bean.factory.LifeCycleMethodExecutor;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 05-03-2023
 */
public class DefaultLifeCycleMethodExecutor implements LifeCycleMethodExecutor {

    private static final Logger logger = LogFactory.getDefaultLogger();

    private final Collection<BeanLifeCycleMethodExecutor> lifeCycleMethodExecutors;

    public DefaultLifeCycleMethodExecutor() {
        logger.info(getClass(), "Initializing started");

        this.lifeCycleMethodExecutors = getLifeCycleMethodExecutors();
        Assert.nonEmpty(lifeCycleMethodExecutors, "LifeCycleMethodExecutors can't be null or empty");

        logger.debug(
                DefaultLifeCycleMethodExecutor.class,
                "initialization successful with " + lifeCycleMethodExecutors.size() + " lifecycle method executors"
        );
    }

    @Override
    public void executeInitMethods(final BeanDefinition beanDefinition) {
        Assert.notNull(beanDefinition, "BeanDefinition can't be null");

        logger.debug(DefaultLifeCycleMethodExecutor.class, "starting execution of init methods for bean '" + beanDefinition.getBeanName() + "'");

        this.lifeCycleMethodExecutors
                .forEach(lifeCycleMethodExecutor ->
                                 lifeCycleMethodExecutor.executeInitMethod(beanDefinition));
    }

    @Override
    public void executeDestroyMethods(final BeanDefinition beanDefinition) {
        Assert.notNull(beanDefinition, "BeanDefinition can't be null");

        logger.debug(
                DefaultLifeCycleMethodExecutor.class,
                "starting execution of destroy methods for bean '" + beanDefinition.getBeanName() + "'"
        );

        this.lifeCycleMethodExecutors
                .forEach(lifeCycleMethodExecutor ->
                                 lifeCycleMethodExecutor.executeDestroyMethod(beanDefinition));
    }

    private Collection<BeanLifeCycleMethodExecutor> getLifeCycleMethodExecutors() {
        final Collection<BeanLifeCycleMethodExecutor> executors = new ArrayList<>();

        executors.add(new JavaSpecificationLifecycleMethodsExecutor());
        executors.add(new SpringBeanLifeCycleMethodExecutor());
        executors.add(new BeanAnnotationLifecycleMethodExecutor());

        return executors;
    }

}
