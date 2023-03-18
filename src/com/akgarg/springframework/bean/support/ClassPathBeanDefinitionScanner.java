package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.context.ResourceLoader;
import com.akgarg.springframework.context.support.SystemResourcesLoader;
import com.akgarg.springframework.util.Assert;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class ClassPathBeanDefinitionScanner {

    private final ConfigurableBeanFactory beanFactory;
    private final ResourceLoader resourceLoader;

    public ClassPathBeanDefinitionScanner(final ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.resourceLoader = new SystemResourcesLoader();

        Assert.notNull(this.beanFactory, "BeanFactory can't be null");
        Assert.notNull(this.resourceLoader, "ResourceLoader can't be null");
    }

    public void scan(final String... basePackages) {
        // todo: implement method
        throw new UnsupportedOperationException("Scanning bean is not yet supported");
    }

}
