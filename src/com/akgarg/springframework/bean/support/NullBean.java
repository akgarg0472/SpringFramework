package com.akgarg.springframework.bean.support;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class NullBean {

    private static NullBean bean;

    private NullBean() {
    }

    public static NullBean getInstance() {
        if (bean == null) {
            bean = new NullBean();
        }

        return bean;
    }

    @Override
    public String toString() {
        return "null";
    }

}
