package com.akgarg.springframework.bean.factory;

import com.akgarg.springframework.bean.support.BeanDefinitionHolder;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public interface ConfigurableBeanFactory extends BeanFactory, BeanDefinitionRegistry {

    ConfigurableBeanFactory getBeanFactory();

    BeanDefinition registerMethodBeanDefinition(Object instance, Method method);

    void setBeanDefinitionHolders(Collection<BeanDefinitionHolder> beanDefinitionHolders);

}
