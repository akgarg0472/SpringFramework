package com.akgarg.springframework.context;

import com.akgarg.springframework.context.exceptions.ResourceLoaderException;

import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public interface ResourceLoader {

    Collection<Class<?>> getDeclaredClasses(String packageName) throws ResourceLoaderException;

}
