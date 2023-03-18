package com.akgarg.springframework.context;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public interface Resource {

    Class<?> getClazz();

    String getPackageName();

}
