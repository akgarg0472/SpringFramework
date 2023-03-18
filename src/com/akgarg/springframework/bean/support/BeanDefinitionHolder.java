package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class BeanDefinitionHolder {

    private final String beanName;
    private final BeanDefinition beanDefinition;

    public BeanDefinitionHolder(final String beanName, final BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }

    public BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }

    public String getBeanName() {
        return this.beanName;
    }

}
