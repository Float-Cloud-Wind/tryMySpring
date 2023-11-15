package com.wsd.beans.factory;

import com.wsd.beans.factory.config.BeanDefinition;
import com.wsd.beans.factory.config.ConfigurableListableBeanFactory;
import com.wsd.beans.factory.support.AbstractBeanDefinition;
import com.wsd.context.support.BeanDefinitionRegistry;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 13:46
 **/
public class DefaultListableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /** Cache of singleton objects: bean name to bean instance. */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** List of bean definition names, in registration order. */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /** Map of bean definition objects, keyed by bean name. */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    public Map<String, Object> getSingletonObjects(){
        return this.singletonObjects;
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    protected Object getBean(String beanName) {
        Object beanInstance = null;

        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        String beanClassName = ((AbstractBeanDefinition)beanDefinition).getBeanClassName();

        Class clazz = null;

        try{
            clazz = Class.forName(beanClassName);
            // 获取无参构造方法
            Constructor constructor = clazz.getDeclaredConstructor();
            // 设置访问权限，因为无参构造方法可能是私有的
            constructor.setAccessible(true);
            // 使用构造方法创建实例
            beanInstance = constructor.newInstance();

            //保存bean
            this.singletonObjects.put(beanName, beanInstance);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return beanInstance;
    }


    //---------------------------------------------------------------------
    // Implementation of BeanDefinitionRegistry interface
    //---------------------------------------------------------------------
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);
        //如果已经存在相同 beanName 的 bean
        if (existingDefinition != null) {
            System.out.println("registerBeanDefinition : beanName already exist");
        }
        else {
            this.beanDefinitionMap.put(beanName, beanDefinition);
            this.beanDefinitionNames.add(beanName);
        }
    }

    @Override
    public void preInstantiateSingletons() {
        List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
        for (String beanName : beanNames) {
            getBean(beanName);
        }

    }
}
