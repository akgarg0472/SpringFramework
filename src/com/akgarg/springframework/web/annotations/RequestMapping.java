package com.akgarg.springframework.web.annotations;

import com.akgarg.springframework.web.HttpRequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value() default "";

    HttpRequestMethod[] method() default {};

    String[] consumes() default {};

    String[] produces() default {};

}
