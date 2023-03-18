package com.akgarg.springframework.context.annotations;

import com.akgarg.springframework.bean.support.ClassPathBeanDefinitionScanner;
import com.akgarg.springframework.context.support.GenericApplicationContext;
import com.akgarg.springframework.logger.LogLevel;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext {

    private static final Logger logger = LogFactory.getDefaultLogger();

    static {
        LogFactory.setLogLevel(LogLevel.INFO);
        logger.info(AnnotationConfigApplicationContext.class, "Starting initializing Spring container");
    }

    private final ClassPathBeanDefinitionScanner beanDefinitionScanner;
    private final AnnotatedBeanDefinitionReader beanDefinitionReader;

    public AnnotationConfigApplicationContext() {
        super(false);

        this.beanDefinitionScanner = new ClassPathBeanDefinitionScanner(this);
        this.beanDefinitionReader = new AnnotatedBeanDefinitionReader(this);

        Assert.notNull(this.beanDefinitionScanner, "BeanDefinitionScanner can't be null");
        Assert.notNull(this.beanDefinitionReader, "BeanDefinitionReader can't be null");
    }

    public AnnotationConfigApplicationContext(String... basePackages) {
        this();
        init(basePackages);
        invokeAfterBeansCreationMethods();
        printContextStartupTime();
    }

    public AnnotationConfigApplicationContext(Class<?>... classes) {
        this();
        init(classes);
        invokeAfterBeansCreationMethods();
        printContextStartupTime();
    }

    private void init(final Class<?>[] classes) {
        this.beanDefinitionReader.read(classes);
    }

    private void init(final String[] basePackages) {
        beanDefinitionScanner.scan(basePackages);
    }

    private void printContextStartupTime() {
        final long startupDuration = System.currentTimeMillis() - getStartTimestamp();

        logger.info(
                AnnotationConfigApplicationContext.class,
                "ApplicationContext took " + startupDuration + "ms for initialization"
        );
    }

}
