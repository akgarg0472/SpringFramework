package com.akgarg.springframework.bean.factory;

import java.util.Map;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public interface BeanFactory {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    Object getBean(String name);

    <T> T getBean(Class<T> type);

    <T> T getBean(String name, Class<T> type);

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(final Class<T> type);

    <T> Map<String, BeanDefinition> getBeanDefinitionsOfType(final Class<T> type);

}
