package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanResolverMetadata;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class BeanResolverMetadataBuilder {

    private BeanResolverMetadataBuilder() {
        throw new UnsupportedOperationException();
    }

    public static BeanResolverMetadata of(
            final Object instance,
            final Method method,
            final String beanName,
            final String resolveType,
            final String beanInitMethod,
            final boolean autowireCandidate
    ) {
        return new BeanResolverMetadata() {

            @Override
            public String getResolveType() {
                return resolveType;
            }

            @Override
            public Object getInstance() {
                return instance;
            }

            @Override
            public Method getMethod() {
                return method;
            }

            @Override
            public String getBeanName() {
                return beanName;
            }

            @Override
            public String getBeanInitMethod() {
                return beanInitMethod;
            }

            @Override
            public boolean isAutowiredCandidate() {
                return autowireCandidate;
            }
        };
    }

}
