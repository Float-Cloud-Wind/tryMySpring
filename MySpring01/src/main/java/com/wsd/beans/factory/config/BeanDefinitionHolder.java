package com.wsd.beans.factory.config;

import com.wsd.beans.BeanMetadataElement;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-12 23:04
 **/
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    private final String[] aliases;


    /**
     * Create a new BeanDefinitionHolder.
     * @param beanDefinition the BeanDefinition to wrap
     * @param beanName the name of the bean, as specified for the bean definition
     * @param aliases alias names for the bean, or {@code null} if none
     */
    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }
}
