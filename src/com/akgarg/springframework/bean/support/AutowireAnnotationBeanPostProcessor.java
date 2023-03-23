package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.NoBeanDefinitionFoundException;
import com.akgarg.springframework.bean.exception.NoSuchBeanDefinitionException;
import com.akgarg.springframework.bean.exception.NoUniqueBeanDefinitionException;
import com.akgarg.springframework.bean.exception.NoUniquePrimaryBeanFoundException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanFactory;
import com.akgarg.springframework.bean.factory.annotation.Autowired;
import com.akgarg.springframework.bean.factory.annotation.Qualifier;
import com.akgarg.springframework.context.exceptions.DependencyInjectionException;
import com.akgarg.springframework.context.exceptions.UnsatisfiedDependencyException;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class AutowireAnnotationBeanPostProcessor {

    private static final Logger logger = LogFactory.getDefaultLogger();

    private final BeanFactory beanFactory;

    public AutowireAnnotationBeanPostProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        Assert.notNull(beanFactory, "BeanFactory shouldn't be null");
    }

    public void process(final BeanDefinition beanDefinition) {
        Assert.notNull(beanDefinition, "BeanDefinition shouldn't be null");
        Assert.notNull(beanDefinition.getBean(), "BeanDefinition bean shouldn't be null");
        Assert.notNull(beanDefinition.getBeanName(), "BeanDefinition bean name shouldn't be null");

        doResolve(beanDefinition);
    }

    private void doResolve(final BeanDefinition beanDefinition) {
        if (beanDefinition.getBean() instanceof NullBean) {
            logger.trace(AutowireAnnotationBeanPostProcessor.class, "Found bean '" + beanDefinition.getBeanName() + "' instance of " + NullBean.class.getName() + ". Returning without processing");
            return;
        }

        final Class<?> beanClass = beanDefinition.getBean().getClass();
        final Field[] declaredFields = beanClass.getDeclaredFields();

        for (final Field field : declaredFields) {
            if (isFieldCandidateToBeAutowired(field)) {
                if (isFieldAnnotatedWithQualifierAnnotation(field)) {
                    injectFieldUsingQualifierAnnotation(beanDefinition, field);
                } else {
                    injectFieldWithoutUsingQualifierAnnotation(beanDefinition, field);
                }
            }
        }
    }

    private void injectFieldWithoutUsingQualifierAnnotation(
            final BeanDefinition beanDefinition, final Field field
    ) {
        final String beanName = beanDefinition.getBeanName();
        final Class<?> beanTypeToInject = field.getType();
        final Map<String, BeanDefinition> beanDefinitionsOfType = this.beanFactory.getBeanDefinitionsOfType(beanTypeToInject);

        if (beanDefinitionsOfType.isEmpty()) {
            throw new UnsatisfiedDependencyException("No Qualifying bean of type " + beanTypeToInject.getName() + " found. Consider defining bean of type " + beanTypeToInject.getName());
        }

        if (beanDefinitionsOfType.size() > 1 && !hasPrimaryBean(beanDefinitionsOfType)) {
            throw new NoUniqueBeanDefinitionException("No unique bean found for type " + beanTypeToInject.getName() + ", Expecting 1 bean but found " + beanDefinitionsOfType.size() + " beans " + beanDefinitionsOfType.keySet());
        }

        final Object dependency = getDependencyBean(beanDefinitionsOfType);

        try {
            logger.debug(
                    AutowireAnnotationBeanPostProcessor.class,
                    "Autowiring dependency with name='" + beanName + "' of type '" + beanTypeToInject.getName() + "' in bean '" + beanName + "'"
            );
            field.setAccessible(true);
            field.set(beanDefinition.getBean(), dependency);

        } catch (IllegalAccessException e) {
            throw new DependencyInjectionException("Exception occurred while injecting bean with type '" + beanTypeToInject.getName() + "' in '" + beanName + "' bean", e);
        }
    }

    private void injectFieldUsingQualifierAnnotation(final BeanDefinition beanDefinition, final Field field) {
        final String beanNameToInject = getQualifierAnnotation(field).value();
        final Class<?> expectedBeanClass = field.getType();
        final String beanName = beanDefinition.getBeanName();

        try {
            final Object beanFetchFromContainerByBeanName = this.beanFactory.getBean(beanNameToInject);

            if (beanFetchFromContainerByBeanName.getClass() != expectedBeanClass) {
                throw new UnsatisfiedDependencyException("Error injecting bean with name '" + beanNameToInject + "' in '" + beanDefinition.getBeanName() + "'. Expecting bean of type " + expectedBeanClass.getName() + " but found " + beanFetchFromContainerByBeanName.getClass().getName());
            }

            logger.debug(
                    AutowireAnnotationBeanPostProcessor.class,
                    "Autowiring dependency with name=" + beanNameToInject + " of type '" + expectedBeanClass.getName() + "' in " + beanName
            );

            field.setAccessible(true);
            field.set(beanDefinition.getBean(), beanFetchFromContainerByBeanName);

        } catch (NoSuchBeanDefinitionException e) {
            throw new UnsatisfiedDependencyException("No Qualifying bean of name '" + beanNameToInject + "' found. Consider defining appropriate bean", e);
        } catch (IllegalAccessException e) {
            throw new DependencyInjectionException("Exception occurred while injecting bean with name '" + beanNameToInject + "' in '" + beanDefinition.getBeanName() + "' bean", e);
        }
    }

    private Object getDependencyBean(final Map<String, BeanDefinition> beanDefinitionsOfType) {
        if (beanDefinitionsOfType.size() == 1) {
            return new ArrayList<>(beanDefinitionsOfType.values()).get(0).getBean();
        }

        final List<BeanDefinition> dependencyBeans = beanDefinitionsOfType.values()
                .stream()
                .filter(BeanDefinition::isPrimary)
                .filter(BeanDefinition::isAutowireCandidate)
                .collect(Collectors.toList());

        if (dependencyBeans.isEmpty()) {
            throw new NoBeanDefinitionFoundException("No bean found for required type");
        }

        if (dependencyBeans.size() > 1) {
            final String beanNames = dependencyBeans.stream()
                    .map(BeanDefinition::getBeanName)
                    .collect(Collectors.joining(","));
            throw new NoUniquePrimaryBeanFoundException("No unique primary bean found. Expected one primary bean but found two: " + beanNames);
        }

        return dependencyBeans.get(0).getBean();
    }

    private boolean hasPrimaryBean(final Map<String, BeanDefinition> beans) {
        return beans.values().stream().anyMatch(BeanDefinition::isPrimary);
    }

    private boolean isFieldAnnotatedWithQualifierAnnotation(final Field field) {
        return field.getAnnotation(Qualifier.class) != null;
    }

    private Qualifier getQualifierAnnotation(final Field field) {
        return field.getAnnotation(Qualifier.class);
    }

    private boolean isFieldCandidateToBeAutowired(final Field field) {
        return field.getAnnotation(Autowired.class) != null;
    }

}
