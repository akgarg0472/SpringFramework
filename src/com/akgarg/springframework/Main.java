package com.akgarg.springframework;

import com.akgarg.springframework.context.ApplicationContext;
import com.akgarg.springframework.context.annotations.AnnotationConfigApplicationContext;
import com.akgarg.springframework.logger.support.LogFactory;

import java.util.Arrays;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class Main {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

        printDependencies(context);
        System.out.println("Hello Custom Spring Framework");
        LogFactory.getDefaultLogger().info(Main.class, "Ending application");
    }

    private static void printDependencies(final ApplicationContext context) {
        System.out.println("Defined beans = " + Arrays.toString(context.getBeanDefinitionNames()));
    }

}
