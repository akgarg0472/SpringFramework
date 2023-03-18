package com.akgarg.springframework;

import com.akgarg.springframework.bean.factory.BeanDefinition;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public class Utils {

    static BeanDefinition getBeanDefinition() {
        return new BeanDefinition() {
            @Override
            public String getBeanName() {
                return "samplebean";
            }

            @Override
            public void setBeanName(final String beanName) {

            }

            @Override
            public String getScope() {
                return "singleton";
            }

            @Override
            public void setScope(final String scope) {

            }

            @Override
            public boolean isPrimary() {
                return false;
            }

            @Override
            public void setPrimary(final boolean primary) {

            }

            @Override
            public Object getBean() {
                return new Object();
            }

            @Override
            public void setBean(final Object bean) {

            }

            @Override
            public boolean isSingleton() {
                return true;
            }

            @Override
            public void setSingleton(final boolean singleton) {

            }

            @Override
            public boolean isPrototype() {
                return false;
            }

            @Override
            public void setPrototype(final boolean prototype) {

            }

            @Override
            public boolean isAutowireCandidate() {
                return true;
            }

            @Override
            public void setAutowireCandidate(final boolean autowireCandidate) {

            }

            @Override
            public String getBeanInitMethod() {
                return null;
            }

            @Override
            public void setBeanInitMethod(final String beanInitMethod) {

            }

            @Override
            public String getBeanDestroyMethod() {
                return null;
            }

            @Override
            public void setBeanDestroyMethod(final String beanDestroyMethod) {

            }
        };
    }

}
