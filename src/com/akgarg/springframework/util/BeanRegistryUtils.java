package com.akgarg.springframework.util;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.bean.support.BeanDefinitionHolder;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public final class BeanRegistryUtils {

    private static final Logger logger = LogFactory.getDefaultLogger();

    private BeanRegistryUtils() {
        throw new UnsupportedOperationException();
    }

    public static Collection<? extends BeanDefinitionHolder> getBeanDefinitionHolders(
            final Class<?> clazz, final ConfigurableBeanFactory beanFactory
    ) {
        Assert.notNull(clazz, "Class should be non-null to scan for bean methods");

        final Collection<BeanDefinitionHolder> beanDefinitionHolders = new ArrayList<>();
        final Object instance = beanFactory.register(clazz);
        final Method[] methods = clazz.getDeclaredMethods();

        for (final Method method : methods) {
            if (ReflectionUtils.isMethodBeanCandidate(method)) {
                logger.debug(BeanRegistryUtils.class, "found bean candidate method '" + method.getName() + "'");
                final BeanDefinition beanDefinition = beanFactory.registerMethodBeanDefinition(instance, method);
                final BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition.getBeanName(), beanDefinition);
                beanDefinitionHolders.add(beanDefinitionHolder);
            }
        }

        return beanDefinitionHolders;
    }

}
