package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.factory.BeanResolver;
import com.akgarg.springframework.bean.factory.annotation.Bean;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class DefaultBeanResolver implements BeanResolver {

    @Override
    public String resolveName(final Method method) {
        Assert.notNull(method, "Method can't be null in BeanResolver");
        return this.doResolve(null, method);
    }

    @Override
    public String resolveName(final Class<?> clazz) {
        Assert.notNull(clazz, "Class type can't be null in BeanResolver");
        return this.doResolve(clazz, null);
    }

    @Override
    public String resolveInitMethod(final Method method) {
        String beanInitMethodName = null;
        final Annotation annotation = getBeanAnnotation(null, method);

        if (annotation instanceof Bean) {
            final String initMethod = ((Bean) annotation).initMethod();

            if (StringUtils.isNonBlankString(initMethod)) {
                beanInitMethodName = initMethod;
            }
        }

        return beanInitMethodName;
    }

    @Override
    public String resolveDestroyMethod(final Method method) {
        String beanDestroyMethodName = null;
        final Annotation annotation = getBeanAnnotation(null, method);

        if (annotation instanceof Bean) {
            final String destroyMethod = ((Bean) annotation).destroyMethod();

            if (StringUtils.isNonBlankString(destroyMethod)) {
                beanDestroyMethodName = destroyMethod;
            }
        }

        return beanDestroyMethodName;
    }

    @Override
    public boolean resolveAutowiredCandidate(final Method method) {
        boolean autowiredCandidate = true;

        final Annotation annotation = getBeanAnnotation(null, method);

        if (annotation instanceof Bean) {
            autowiredCandidate = ((Bean) annotation).autowiredCandidate();
        }

        return autowiredCandidate;
    }

    private String doResolve(final Class<?> clazz, final Method method) {
        String beanName = getDefaultBeanName(clazz, method);
        final Annotation annotation = getBeanAnnotation(clazz, method);

        if (annotation instanceof Bean) {
            final String beanAnnotationBeanName = ((Bean) annotation).name();

            if (StringUtils.isNonBlankString(beanAnnotationBeanName)) {
                beanName = beanAnnotationBeanName;
            }
        }

        return beanName;
    }

    private Annotation getBeanAnnotation(final Class<?> clazz, final Method method) {
        if (clazz != null) {
            return clazz.getAnnotation(Bean.class);
        } else {
            return method.getAnnotation(Bean.class);
        }
    }

    private String getDefaultBeanName(final Class<?> clazz, final Method method) {
        if (clazz != null) return StringUtils.convertStringToCamelCase(clazz.getSimpleName());
        else {
            return StringUtils.convertStringToCamelCase(method.getName());
        }
    }

}
