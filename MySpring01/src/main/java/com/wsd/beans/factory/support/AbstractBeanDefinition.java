package com.wsd.beans.factory.support;

import com.wsd.beans.factory.config.BeanDefinition;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-13 00:13
 **/
public abstract class AbstractBeanDefinition implements BeanDefinition {

    private volatile Object beanClass;

    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    public String getBeanClassName() {
        return (this.beanClass instanceof Class<?>  ? ((Class<?>)this.beanClass).getName() : (String) this.beanClass);
    }
}
