package com.akgarg.springframework.context.support;

import com.akgarg.springframework.context.ResourceLoader;
import com.akgarg.springframework.context.exceptions.ResourceLoaderException;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.ClassLoaderUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public final class SystemResourcesLoader implements ResourceLoader {

    @Override
    public Collection<Class<?>> getDeclaredClasses(final String packageName) throws ResourceLoaderException {
        try {
            final ClassLoader classLoader = ClassLoaderUtils.getClassLoader();
            Assert.notNull(classLoader, "ClassLoader can't be null in ResourceLoader");

            final String path = packageName.replace('.', '/');
            final Enumeration<URL> resources = classLoader.getResources(path);
            final List<File> directories = new ArrayList<>();

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                directories.add(new File(resource.getFile()));
            }

            final List<Class<?>> classes = new ArrayList<>();

            for (File directory : directories) {
                classes.addAll(findClasses(directory, packageName));
            }

            return classes;

        } catch (Throwable e) {
            throw new ResourceLoaderException("Error loading resources using resource loader", e);
        }
    }

    private Collection<Class<?>> findClasses(
            final File directory, final String packageName
    ) throws ClassNotFoundException {
        final List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        final File[] files = directory.listFiles();

        if (files == null) {
            return classes;
        }

        for (final File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }

        return classes;
    }

}
