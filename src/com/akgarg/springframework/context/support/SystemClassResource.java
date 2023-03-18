package com.akgarg.springframework.context.support;

import com.akgarg.springframework.context.Resource;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
final class SystemClassResource implements Resource {

    private final Class<?> clazz;
    private final String packageName;

    public SystemClassResource(final Class<?> clazz) {
        this.clazz = clazz;
        this.packageName = clazz.getPackage().getName();
    }

    @Override
    public Class<?> getClazz() {
        return this.clazz;
    }

    @Override
    public String getPackageName() {
        return this.packageName;
    }

}
