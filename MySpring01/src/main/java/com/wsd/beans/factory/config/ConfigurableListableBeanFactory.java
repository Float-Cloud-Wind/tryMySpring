package com.wsd.beans.factory.config;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 13:48
 **/
public interface ConfigurableListableBeanFactory {

    /**
     * Ensure that all non-lazy-init singletons are instantiated.
     */
    void preInstantiateSingletons();
}
