package com.akgarg.springframework.bean.factory;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public interface BeanDefinitionResolver {

    BeanDefinition resolve(BeanResolverMetadata beanResolverMetadata);

}
