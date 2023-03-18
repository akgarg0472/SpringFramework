package com.akgarg.springframework.util;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.bean.support.BeanDefinitionHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public final class BeanRegistryUtils {

    private BeanRegistryUtils() {
        throw new UnsupportedOperationException();
    }

    public static Collection<? extends BeanDefinitionHolder> getBeanDefinitionHolders(
            final Class<?> clazz,
            final ConfigurableBeanFactory beanFactory
    ) {
        Assert.notNull(clazz, "Class should be non-null to scan for bean methods");

        final Object instance = beanFactory.register(clazz);

        final Collection<BeanDefinitionHolder> beanDefinitionHolders = new ArrayList<>();
        final Method[] methods = clazz.getDeclaredMethods();

        for (final Method method : methods) {
            if (ReflectionUtils.isMethodBeanCandidate(method)) {
                final BeanDefinition beanDefinition = beanFactory.registerMethodBeanDefinition(instance, method);
                final BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition.getBeanName(), beanDefinition);
                beanDefinitionHolders.add(beanDefinitionHolder);
            }
        }

        return beanDefinitionHolders;
    }

}
