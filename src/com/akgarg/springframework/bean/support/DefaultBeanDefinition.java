package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanDefinition;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class DefaultBeanDefinition implements BeanDefinition {

    private String beanName;
    private String scope;
    private boolean primary;
    private boolean singleton;
    private boolean prototype;
    private boolean autowireCandidate;
    private Object bean;
    private String beanInitMethod;
    private String beanDestroyMethod;
    
    public DefaultBeanDefinition() {
        this.setScope(DefaultBeanFactory.SCOPE_SINGLETON);
        this.setSingleton(true);
        this.setPrimary(false);
        this.setPrototype(false);
        this.setAutowireCandidate(true);
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public void setBeanName(final String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(final String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isPrimary() {
        return this.primary;
    }

    @Override
    public void setPrimary(final boolean primary) {
        this.primary = primary;
    }

    @Override
    public Object getBean() {
        return this.bean;
    }

    @Override
    public void setBean(final Object bean) {
        this.bean = bean;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public void setSingleton(final boolean singleton) {
        this.singleton = singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public void setPrototype(final boolean prototype) {
        this.prototype = prototype;
    }

    @Override
    public boolean isAutowireCandidate() {
        return this.autowireCandidate;
    }

    @Override
    public void setAutowireCandidate(final boolean autowireCandidate) {
        this.autowireCandidate = autowireCandidate;
    }

    @Override
    public String getBeanInitMethod() {
        return this.beanInitMethod;
    }

    @Override
    public void setBeanInitMethod(final String beanInitMethod) {
        this.beanInitMethod = beanInitMethod;
    }

    @Override
    public String getBeanDestroyMethod() {
        return this.beanDestroyMethod;
    }

    @Override
    public void setBeanDestroyMethod(final String beanDestroyMethod) {
        this.beanDestroyMethod = beanDestroyMethod;
    }

}
