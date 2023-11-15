package com.wsd.context.support;

import com.wsd.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * Return the number of beans defined in the registry.
     * @return the number of beans defined in the registry
     */
    int getBeanDefinitionCount();

    /**
    *Register a new bean definition with this registry.
    */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
