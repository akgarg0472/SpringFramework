package com.akgarg.springframework;

import com.akgarg.springframework.bean.factory.InitializingBean;
import com.akgarg.springframework.bean.factory.annotation.Autowired;
import com.akgarg.springframework.bean.factory.annotation.Bean;
import com.akgarg.springframework.bean.factory.annotation.Primary;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
@Primary
public class TestConfig {

    @Bean(name = "beanA")
    A a() {
        return new A();
    }

    @Bean(initMethod = "randomInitMethodTest")
    B b() {
        return new B();
    }

    class A {

        @Autowired
        private B b;

        public B getB() {
            return b;
        }

    }

    class B implements InitializingBean {

        void randomInitMethodTest() {
            System.out.println("@Bean Init method of B called");
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("afterPropertiesSet called");
        }
    }

}
