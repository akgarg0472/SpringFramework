package com.akgarg.springframework.context.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.LifeCycleMethodExecutor;
import com.akgarg.springframework.bean.support.AutowireAnnotationBeanPostProcessor;
import com.akgarg.springframework.bean.support.BeanDefinitionHolder;
import com.akgarg.springframework.bean.support.DefaultBeanFactory;
import com.akgarg.springframework.bean.support.DefaultLifeCycleMethodExecutor;
import com.akgarg.springframework.context.ApplicationContext;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class GenericApplicationContext extends DefaultBeanFactory implements ApplicationContext {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final LifeCycleMethodExecutor lifeCycleMethodsExecutor;
    private final AutowireAnnotationBeanPostProcessor autowireAnnotationBeanPostProcessor;

    public GenericApplicationContext(
            final boolean allowBeanOverriding
    ) {
        super(allowBeanOverriding);
        this.lifeCycleMethodsExecutor = new DefaultLifeCycleMethodExecutor();
        this.autowireAnnotationBeanPostProcessor = new AutowireAnnotationBeanPostProcessor(getBeanFactory());
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

    protected void invokeAfterBeansCreationMethods() {
        logger.debug(getClass(), "Starting execution of invokeAfterBeansCreationMethods()");

        getBeanDefinitionsMap().forEach((beanName, beanDefinition) -> {
            logger.trace(getClass(), "Executing after bean creation methods for '" + beanName + "' bean");
            injectDependencies(beanName, beanDefinition);
            invokeBeanLifeCycleMethods(beanName, beanDefinition);
        });
    }

    protected void registerShutdownHook() {
        logger.debug(GenericApplicationContext.class, "Destroying beans.....");

        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> getBeanDefinitionsMap().forEach((beanName, beanDefinition) -> {
                    logger.trace(GenericApplicationContext.class, "Destroying bean: " + beanName);
                    lifeCycleMethodsExecutor.executeDestroyMethods(beanDefinition);
                })));
    }

    private void injectDependencies(
            final String beanName,
            final BeanDefinition beanDefinition
    ) {
        logger.debug(getClass(), "Starting dependency injection in '" + beanName + "'");
        this.autowireAnnotationBeanPostProcessor.process(beanDefinition);
    }

    private void invokeBeanLifeCycleMethods(
            final String beanName,
            final BeanDefinition beanDefinition
    ) {
        logger.debug(getClass(), "Invoking bean life cycle methods for '" + beanName + "' bean");
        this.lifeCycleMethodsExecutor.executeInitMethods(beanDefinition);
    }

    protected void initBeans() {
        final Collection<BeanDefinitionHolder> beanDefinitionHolders = getBeanDefinitionHolders();
        logger.debug(GenericApplicationContext.class, "Starting spring bean registration process...");
        beanDefinitionHolders.forEach(this::register);
    }

    private void register(final BeanDefinitionHolder holder) {
        logger.debug(GenericApplicationContext.class, "Registering bean " + holder.getBeanName());
        registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition());
    }

}
