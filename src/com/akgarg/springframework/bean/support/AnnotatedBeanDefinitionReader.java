package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.BeanRegistryUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class AnnotatedBeanDefinitionReader {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final ConfigurableBeanFactory beanFactory;

    public AnnotatedBeanDefinitionReader(final ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        Assert.notNull(this.beanFactory, "ConfigurableBeanFactory can't be null");
    }

    public void read(final Class<?>[] classes) {
        logger.info(AnnotatedBeanDefinitionReader.class, "Starting scanning bean config class(s): " + Arrays.toString(classes));

        final Set<BeanDefinitionHolder> beanDefinitionHolders = new HashSet<>();

        for (final Class<?> clazz : classes) {
            Assert.notNull(clazz, "Bean Definition configuration class can't be null");
            final Collection<? extends BeanDefinitionHolder> holders = doRead(clazz);
            beanDefinitionHolders.addAll(holders);
        }

        logger.info(
                AnnotatedBeanDefinitionReader.class,
                "Scanning completed successfully and found " + beanDefinitionHolders.size() + " beans eligible for registration"
        );

        beanFactory.setBeanDefinitionHolders(beanDefinitionHolders);
    }

    private Collection<? extends BeanDefinitionHolder> doRead(final Class<?> clazz) {
        return BeanRegistryUtils.getBeanDefinitionHolders(clazz, beanFactory);
    }

}
