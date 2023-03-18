package com.akgarg.springframework.context.annotations;

import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.bean.support.BeanDefinitionHolder;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.BeanRegistryUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class AnnotatedBeanDefinitionReader {

    private final ConfigurableBeanFactory beanFactory;

    public AnnotatedBeanDefinitionReader(final ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        Assert.notNull(this.beanFactory, "ConfigurableBeanFactory can't be null");
    }

    public void read(final Class<?>[] classes) {
        final Set<BeanDefinitionHolder> beanDefinitionHolders = new HashSet<>();

        for (final Class<?> clazz : classes) {
            Assert.notNull(clazz, "Bean Definition configuration class can't be null");

            final Collection<? extends BeanDefinitionHolder> holders = doRead(clazz);
            beanDefinitionHolders.addAll(holders);
        }

        beanDefinitionHolders.forEach(holder -> beanFactory.registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition()));
    }

    private Collection<? extends BeanDefinitionHolder> doRead(final Class<?> clazz) {
        return BeanRegistryUtils.getBeanDefinitionHolders(clazz, beanFactory);
    }

}
