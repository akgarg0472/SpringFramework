package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanLifeCycleException;
import com.akgarg.springframework.bean.exception.InvalidBeanLifeCycleMethodException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanLifeCycleMethodExecutor;
import com.akgarg.springframework.bean.factory.DisposableBean;
import com.akgarg.springframework.bean.factory.InitializingBean;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Akhilesh Garg
 * @since 04-03-2023
 */
public class SpringBeanLifeCycleMethodExecutor implements BeanLifeCycleMethodExecutor {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private static final String INIT_METHOD_NAME = "afterPropertiesSet";
    private static final String DESTROY_METHOD_NAME = "destroy";

    @Override
    public void executeInitMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.debug(SpringBeanLifeCycleMethodExecutor.class, "Starting executing executeInitMethod for bean '" + beanName + "'");

        try {
            final Object bean = beanDefinition.getBean();

            if (!(bean instanceof InitializingBean)) {
                logger.trace(SpringBeanLifeCycleMethodExecutor.class, "Bean '" + beanName + "' is not implementing " + InitializingBean.class.getName() + ". Returning as it is without doing anything");
                return;
            }

            this.invokeMethod(bean, INIT_METHOD_NAME);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean init life cycle method for bean '" + beanName + "'", e);
        }
    }

    @Override
    public void executeDestroyMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.trace(SpringBeanLifeCycleMethodExecutor.class, "Starting executing executeDestroyMethod for bean '" + beanName + "'");

        try {
            final Object bean = beanDefinition.getBean();

            if (!(bean instanceof DisposableBean)) {
                logger.trace(SpringBeanLifeCycleMethodExecutor.class, "Bean '" + beanName + "' is not implementing " + DisposableBean.class.getName() + ". Returning as it is without doing anything");
                return;
            }

            this.invokeMethod(bean, DESTROY_METHOD_NAME);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean destroy life cycle method for bean '" + beanName + "'", e);
        }
    }

    private void invokeMethod(
            final Object bean, final String destroyMethodName
    ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = bean.getClass().getMethod(destroyMethodName);
        final int parameterCount = method.getParameterCount();

        if (parameterCount > 0) {
            throw new InvalidBeanLifeCycleMethodException("lifecycle method can't have any parameters. Expecting 0 params but found " + parameterCount + " -> " + Arrays.toString(method.getParameters()));
        }

        ReflectionUtils.invokeMethod(bean, method);
    }

}
