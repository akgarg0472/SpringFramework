package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanCreationException;
import com.akgarg.springframework.bean.exception.BeanNotOfRequiredTypeException;
import com.akgarg.springframework.bean.exception.NoSuchBeanDefinitionException;
import com.akgarg.springframework.bean.exception.NoUniqueBeanDefinitionException;
import com.akgarg.springframework.bean.factory.*;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class DefaultBeanFactory implements ConfigurableBeanFactory {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final Map<String, BeanDefinition> beanDefinitionMap;
    private final boolean allowBeanOverriding;
    private final long startTimestamp;
    private final BeanResolver beanResolver;
    private final BeanDefinitionResolver beanDefinitionResolver;

    public DefaultBeanFactory(final boolean allowBeanOverriding) {
        logger.debug(getClass(), "Starting initialization of DefaultBeanFactory");

        this.startTimestamp = System.currentTimeMillis();
        this.beanDefinitionMap = new LinkedHashMap<>();
        this.allowBeanOverriding = allowBeanOverriding;
        this.beanResolver = new DefaultBeanResolver();
        this.beanDefinitionResolver = new BeanAnnotationBeanDefinitionResolver();

        Assert.notNull(this.beanResolver, "BeanResolver can't be null");
        Assert.notNull(this.beanDefinitionResolver, "BeanDefinitionResolver can't be null");

        logger.debug(getClass(), "Initialization of DefaultBeanFactory completed successfully");
    }

    @Override
    public Object getBean(final String name) {
        logger.debug(getClass(), "Getting bean with name=" + name);

        final BeanDefinition beanDefinition = this.beanDefinitionMap.get(name);

        if (beanDefinition == null || beanDefinition.getBean() == null) {
            throw new NoSuchBeanDefinitionException("No bean found for name=" + name + "'. Consider declaring bean with name '" + name + "'");
        }

        return beanDefinition.getBean();
    }

    @Override
    public <T> T getBean(final Class<T> type) {
        logger.debug(getClass(), "Getting bean of type '" + type + "'");

        final Map<String, T> beans = this.getBeansOfType(type);

        if (beans.isEmpty()) {
            final String message = "No bean found of type " + type.getName();
            logger.fatal(getClass(), message);
            throw new NoSuchBeanDefinitionException(message);
        }

        if (beans.size() > 1) {
            final String message = "No unique bean found for type " + type.getName() + ", Expecting 1 bean but found " + beans.size() + " beans " + beans.keySet();
            logger.fatal(getClass(), message);
            throw new NoUniqueBeanDefinitionException(message);
        }

        return new ArrayList<>(beans.values()).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(final String beanName, final Class<T> type) {
        logger.debug(getClass(), "Getting bean with name=" + beanName + " and type=" + type);

        Assert.nonEmpty(beanName, "Bean name should be a valid string");
        Assert.notNull(type, "Bean type should be non null");

        final BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        if (beanDefinition == null || beanDefinition.getBean() == null) {
            final String message = "No bean found with name '" + beanName + "'";
            logger.fatal(getClass(), message);
            throw new NoSuchBeanDefinitionException(message);
        }

        final Object bean = beanDefinition.getBean();

        try {
            return (T) bean;
        } catch (ClassCastException e) {
            final String message = "Bean " + "'" + beanName + "' is expected of type '" + type.getName() + "' but was actually of type '" + bean.getClass().getName() + "'";
            logger.fatal(getClass(), message);
            throw new BeanNotOfRequiredTypeException(message);
        }
    }

    @Override
    public String[] getBeanNamesForType(final Class<?> type) {
        logger.debug(getClass(), "Getting bean names list for type '" + type + "'");

        final Set<String> beansOfType = this.getBeansOfType(type).keySet();
        final String[] beanNames = new String[beansOfType.size()];
        int idx = 0;

        for (final String beanName : beansOfType) {
            beanNames[idx++] = beanName;
        }

        return beanNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(final Class<T> type) {
        logger.debug(getClass(), "Getting bean names map for type '" + type + "'");

        final Map<String, BeanDefinition> beanDefinitionsOfType = this.getBeanDefinitionsOfType(type);
        final Map<String, T> beansOfType = new HashMap<>(beanDefinitionsOfType.size());

        beanDefinitionsOfType.forEach((beanName, beanDefinition) -> {
            beansOfType.put(beanName, (T) beanDefinition.getBean());
        });

        return beansOfType;
    }

    @Override
    public <T> Map<String, BeanDefinition> getBeanDefinitionsOfType(final Class<T> type) {
        logger.debug(getClass(), "Getting bean definitions for type '" + type + "'");

        final Set<Map.Entry<String, BeanDefinition>> beans = this.beanDefinitionMap.entrySet();
        final Map<String, BeanDefinition> beanDefinitionsOfTypeMap = new HashMap<>();

        for (final Map.Entry<String, BeanDefinition> entry : beans) {
            final BeanDefinition beanDefinition = entry.getValue();

            if (beanDefinition.getBean().getClass() == type) {
                beanDefinitionsOfTypeMap.put(entry.getKey(), beanDefinition);
            }
        }

        return beanDefinitionsOfTypeMap;
    }

    @Override
    public void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        logger.debug(getClass(), "Registering bean definition with name = " + beanName);

        final Object existingBean = this.beanDefinitionMap.get(beanName);

        if (existingBean != null) {
            logger.debug(getClass(), "existing bean found with name=" + beanName);

            if (allowBeanOverriding) {
                logger.debug(getClass(), "overriding existing bean having name " + beanName + " with new instance");
                this.beanDefinitionMap.put(beanName, beanDefinition);
            } else {
                final String message = "Bean with name '" + beanName + "' already exists. Consider defining bean with other name";
                logger.fatal(getClass(), message);
                throw new BeanCreationException(message);
            }
        } else {
            logger.debug(getClass(), "adding bean definition with name=" + beanName + " in registry");
            this.beanDefinitionMap.put(beanName, beanDefinition);
        }
    }

    @Override
    public void unregisterBeanDefinition(final String beanName) {
        logger.trace(getClass(), "unregistering bean with name '" + beanName + "'");

        if (this.containsBeanDefinition(beanName)) {
            logger.debug(getClass(), "removing bean with name '" + beanName + "' from registry");
            this.beanDefinitionMap.remove(beanName);
        }
    }

    @Override
    public boolean containsBeanDefinition(final String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        final Set<String> beanNames = this.beanDefinitionMap.keySet();
        final String[] beanDefinitionNames = new String[beanNames.size()];
        int idx = 0;

        for (final String beanName : beanNames) {
            beanDefinitionNames[idx++] = beanName;
        }

        return beanDefinitionNames;
    }

    @Override
    public Object register(final Class<?> clazz) {
        logger.trace(getClass(), "registering bean for class '" + clazz.getName() + "'");

        final String beanName = this.beanResolver.resolveName(clazz);
        final Object instance = ReflectionUtils.createInstanceForClass(clazz);

        final BeanResolverMetadata beanResolverMetadata = getBeanResolverMetaData(
                instance,
                null,
                beanName,
                BeanResolverMetadata.RESOLVE_CLASS_TYPE,
                null,
                null,
                true
        );

        final BeanDefinition beanDefinition = this.beanDefinitionResolver.resolve(beanResolverMetadata);

        logger.debug(getClass(), "registering bean for class '" + clazz.getName() + "' with name '" + beanName + "'");

        this.registerBeanDefinition(beanName, beanDefinition);

        return instance;
    }

    @Override
    public ConfigurableBeanFactory getBeanFactory() {
        return this;
    }

    @Override
    public BeanResolver getBeanNameResolver() {
        return this.beanResolver;
    }

    @Override
    public BeanDefinitionResolver getBeanDefinitionResolver() {
        return this.beanDefinitionResolver;
    }

    @Override
    public BeanDefinition registerMethodBeanDefinition(final Object instance, final Method method) {
        Assert.notNull(instance, "Instance can't be null");
        Assert.notNull(method, "Method can't be null");
        logger.trace(getClass(), "registering bean for the method '" + method.getName() + "'");

        final String beanName = this.beanResolver.resolveName(method);
        final String beanInitMethod = this.beanResolver.resolveInitMethod(method);
        final String beanDestroyMethod = this.beanResolver.resolveDestroyMethod(method);
        final boolean autowiredCandidate = this.beanResolver.resolveAutowiredCandidate(method);

        final BeanResolverMetadata beanResolverMetadata = getBeanResolverMetaData(
                instance,
                method,
                beanName,
                BeanResolverMetadata.RESOLVE_METHOD_TYPE,
                beanInitMethod,
                beanDestroyMethod,
                autowiredCandidate
        );

        return this.beanDefinitionResolver.resolve(beanResolverMetadata);
    }

    protected Map<String, BeanDefinition> getBeanDefinitionsMap() {
        return this.beanDefinitionMap;
    }

    protected long getStartTimestamp() {
        return this.startTimestamp;
    }

    private BeanResolverMetadata getBeanResolverMetaData(
            final Object instance,
            final Method method,
            final String beanName,
            final String resolveType,
            final String beanInitMethod,
            final String beanDestroyMethod,
            final boolean autowireCandidate
    ) {
        return BeanResolverMetadataBuilder.of(
                instance,
                method,
                beanName,
                resolveType,
                beanInitMethod,
                beanDestroyMethod,
                autowireCandidate
        );
    }

}
