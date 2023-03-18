package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanLifeCycleException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanLifeCycleMethodExecutor;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class JavaSpecificationLifecycleMethodsExecutor implements BeanLifeCycleMethodExecutor {

    private static final Logger logger = LogFactory.getDefaultLogger();

    @Override
    public void executeInitMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.trace(getClass(), "starting executing executeInitMethod for bean '" + beanName + "'");

        try {
            final Object bean = beanDefinition.getBean();

            final Collection<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(_method -> _method.getAnnotation(PostConstruct.class) != null)
                    .collect(Collectors.toList());

            if (methods.isEmpty()) {
                logger.trace(getClass(), "Bean '" + beanName + "' doesn't have @PostConstruct method. Returning without doing anything");
                return;
            }

            this.executeMethods(bean, methods);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean init life cycle method for bean '" + beanName + "'", e);
        }
    }

    @Override
    public void executeDestroyMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.trace(getClass(), "starting executing executeDestroyMethod for bean '" + beanName + "'");

        try {
            final Object bean = beanDefinition.getBean();

            final Collection<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(_method -> _method.getAnnotation(PreDestroy.class) != null)
                    .collect(Collectors.toList());

            if (methods.isEmpty()) {
                logger.trace(getClass(), "Bean '" + beanName + "' doesn't have @PreDestroy method. Returning without doing anything");
                return;
            }

            this.executeMethods(bean, methods);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean destroy life cycle method for bean '" + beanName + "'", e);
        }
    }

    private void executeMethods(
            final Object bean,
            final Collection<Method> methods
    ) throws InvocationTargetException, IllegalAccessException {
        for (final Method method : methods) {
            ReflectionUtils.checkMethodParamsForBeanLifeCycleMethods(method);
            ReflectionUtils.invokeMethod(bean, method);
        }
    }

}
