package com.akgarg.springframework.web.annotations;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public @interface GetMapping {

    String value() default "";

    String[] consumes() default {};

    String[] produces() default {};

}
