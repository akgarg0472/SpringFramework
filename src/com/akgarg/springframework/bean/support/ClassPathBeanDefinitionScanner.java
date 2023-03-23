package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanDefinitionHolderCreatorException;
import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;
import com.akgarg.springframework.context.ResourceLoader;
import com.akgarg.springframework.context.annotations.Configuration;
import com.akgarg.springframework.context.stereoptype.Component;
import com.akgarg.springframework.context.stereoptype.Controller;
import com.akgarg.springframework.context.stereoptype.Service;
import com.akgarg.springframework.context.support.SystemResourcesLoader;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.BeanRegistryUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class ClassPathBeanDefinitionScanner {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final ConfigurableBeanFactory beanFactory;
    private final ResourceLoader resourceLoader;

    public ClassPathBeanDefinitionScanner(final ConfigurableBeanFactory beanFactory) {
        logger.info(ClassPathBeanDefinitionScanner.class, "initializing.....");

        this.beanFactory = beanFactory;
        this.resourceLoader = new SystemResourcesLoader();

        Assert.notNull(this.beanFactory, "BeanFactory can't be null");
        Assert.notNull(this.resourceLoader, "ResourceLoader can't be null");
    }

    public void scan(final String... basePackages) {
        logger.debug(ClassPathBeanDefinitionScanner.class, "Starting class scanning in defined packages: " + Arrays.toString(basePackages));
        final Collection<Class<?>> classes = new HashSet<>();

        for (final String basePackage : basePackages) {
            final Collection<Class<?>> scannedClassesForPackage = this.resourceLoader.getDeclaredClasses(basePackage)
                    .stream()
                    .filter(this::isClassEligibleForBeanDefinition)
                    .collect(Collectors.toList());
            classes.addAll(scannedClassesForPackage);
        }

        final List<String> classNames = classes.stream().map(Class::getName).collect(Collectors.toList());
        this.registerBeanDefinitionHolder(classNames);

        logger.debug(ClassPathBeanDefinitionScanner.class, "ClassPathBeanDefinitionScanner initialization completed and found " + classes.size() + " classes eligible for bean definition: " + classNames);
    }

    private void registerBeanDefinitionHolder(final Collection<String> classes) {
        final Collection<BeanDefinitionHolder> beanDefinitionHolders = new HashSet<>();

        for (final String className : classes) {
            try {
                Assert.nonEmpty(className, "Bean Definition configuration class name can't be null or empty");
                final Class<?> clazz = Class.forName(className);
                Assert.notNull(clazz, "Bean Definition configuration class can't be null");
                final Collection<? extends BeanDefinitionHolder> holders = doRead(clazz);
                beanDefinitionHolders.addAll(holders);
            } catch (Throwable e) {
                logger.error(ClassPathBeanDefinitionScanner.class, "Error in scanning class for bean definition holder: " + className);
                throw new BeanDefinitionHolderCreatorException("Exception occurred in scanning " + className + " class for bean definition holder(s)", e);
            }
        }

        beanFactory.setBeanDefinitionHolders(beanDefinitionHolders);
    }

    private boolean isClassEligibleForBeanDefinition(final Class<?> aClass) {
        try {
            return aClass.getAnnotation(Component.class) != null ||
                    aClass.getAnnotation(Service.class) != null ||
                    aClass.getAnnotation(Configuration.class) != null ||
                    aClass.getAnnotation(Controller.class) != null;
        } catch (Throwable e) {
            return false;
        }
    }

    private Collection<? extends BeanDefinitionHolder> doRead(final Class<?> clazz) {
        return BeanRegistryUtils.getBeanDefinitionHolders(clazz, beanFactory);
    }

}
