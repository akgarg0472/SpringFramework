package com.akgarg.springframework.context.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.LifeCycleMethodExecutor;
import com.akgarg.springframework.bean.support.DefaultBeanFactory;
import com.akgarg.springframework.bean.support.DefaultLifeCycleMethodExecutor;
import com.akgarg.springframework.context.ApplicationContext;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class GenericApplicationContext extends DefaultBeanFactory implements ApplicationContext {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final LifeCycleMethodExecutor lifeCycleMethodsExecutor;

    public GenericApplicationContext(
            final boolean allowBeanOverriding
    ) {
        super(allowBeanOverriding);
        this.lifeCycleMethodsExecutor = new DefaultLifeCycleMethodExecutor();
        Assert.notNull(this.lifeCycleMethodsExecutor, "LifeCycleMethodExecutor can't be null");
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

    public void invokeAfterBeansCreationMethods() {
        logger.debug(getClass(), "Starting execution of invokeAfterBeansCreationMethods()");

        getBeanDefinitionsMap().forEach((beanName, beanDefinition) -> {
            logger.debug(getClass(), "Executing after bean creation methods for '" + beanName + "' bean");
            injectDependencies(beanName, beanDefinition);
            invokeBeanLifeCycleMethods(beanName, beanDefinition);
        });
    }

    private void injectDependencies(final String beanName, final BeanDefinition beanDefinition) {
        logger.debug(getClass(), "Injecting dependencies in '" + beanName + "' bean");
    }

    private void invokeBeanLifeCycleMethods(
            final String beanName, final BeanDefinition beanDefinition
    ) {
        logger.debug(getClass(), "Invoking bean life cycle methods for '" + beanName + "' bean");
        this.lifeCycleMethodsExecutor.executeInitMethods(beanDefinition);
    }

}
