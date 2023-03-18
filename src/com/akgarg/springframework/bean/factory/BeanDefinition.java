package com.akgarg.springframework.bean.factory;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public interface BeanDefinition {

    String getBeanName();

    void setBeanName(String beanName);

    String getScope();

    void setScope(String scope);

    boolean isPrimary();

    void setPrimary(boolean primary);

    Object getBean();

    void setBean(Object bean);

    boolean isSingleton();

    void setSingleton(boolean singleton);

    boolean isPrototype();

    void setPrototype(boolean prototype);

    boolean isAutowireCandidate();

    void setAutowireCandidate(boolean autowireCandidate);

    String getBeanInitMethod();

    void setBeanInitMethod(String beanInitMethod);

    String getBeanDestroyMethod();

    void setBeanDestroyMethod(String beanDestroyMethod);

}
