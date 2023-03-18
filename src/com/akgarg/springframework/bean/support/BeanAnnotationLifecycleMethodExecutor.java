package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanLifeCycleException;
import com.akgarg.springframework.bean.exception.InvalidBeanLifeCycleMethodException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanLifeCycleMethodExecutor;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.ReflectionUtils;
import com.akgarg.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class BeanAnnotationLifecycleMethodExecutor implements BeanLifeCycleMethodExecutor {

    private static final Logger logger = LogFactory.getDefaultLogger();

    @Override
    public void executeInitMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.debug(
                BeanAnnotationLifecycleMethodExecutor.class,
                "starting executeInitMethod for bean '" + beanName + "'"
        );

        try {
            final String beanInitMethod = beanDefinition.getBeanInitMethod();

            if (!StringUtils.isNonBlankString(beanInitMethod)) {
                logger.trace(
                        BeanAnnotationLifecycleMethodExecutor.class,
                        "Bean '" + beanName + "' doesn't have any initMethod defined. Returning as it is without doing anything"
                );
                return;
            }

            final Object bean = beanDefinition.getBean();

            final Method method = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(_method -> _method.getName().equals(beanInitMethod))
                    .findFirst()
                    .orElseThrow(() -> new InvalidBeanLifeCycleMethodException("No init method found with name " + beanInitMethod + " in bean " + beanName));

            ReflectionUtils.checkMethodParamsForBeanLifeCycleMethods(method);
            ReflectionUtils.invokeMethod(bean, method);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean init life cycle method for bean '" + beanName + "'", e);
        }
    }

    @Override
    public void executeDestroyMethod(final BeanDefinition beanDefinition) {
        final String beanName = beanDefinition.getBeanName();
        logger.trace(
                BeanAnnotationLifecycleMethodExecutor.class,
                "starting executing executeDestroyMethod for bean '" + beanName + "'"
        );

        try {
            final String beanDestroyMethod = beanDefinition.getBeanDestroyMethod();

            if (!StringUtils.isNonBlankString(beanDestroyMethod)) {
                logger.trace(
                        BeanAnnotationLifecycleMethodExecutor.class,
                        "Bean '" + beanName + "' doesn't have any destroyMethod defined"
                );
                return;
            }

            final Object bean = beanDefinition.getBean();

            final Method method = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(_method -> _method.getName().equals(beanDestroyMethod))
                    .findFirst()
                    .orElseThrow(() -> new InvalidBeanLifeCycleMethodException("No destroy method found with name " + beanDestroyMethod + " in bean " + beanName));

            ReflectionUtils.checkMethodParamsForBeanLifeCycleMethods(method);
            ReflectionUtils.invokeMethod(bean, method);

        } catch (Throwable e) {
            throw new BeanLifeCycleException("Exception executing bean destroy life cycle method for bean '" + beanName + "'", e);
        }
    }

}
