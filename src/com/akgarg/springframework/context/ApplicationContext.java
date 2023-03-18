package com.akgarg.springframework.context;

import com.akgarg.springframework.bean.factory.ConfigurableBeanFactory;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public interface ApplicationContext extends ConfigurableBeanFactory {

    String getApplicationName();

    String getId();

    String getVersion();

}
