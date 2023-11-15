package com.wsd.context.support;

import com.wsd.beans.factory.DefaultListableBeanFactory;
import com.wsd.beans.factory.config.ConfigurableListableBeanFactory;
import com.wsd.beans.factory.xml.XmlBeanDefinitionReader;
import com.wsd.core.io.DefaultResourceLoader;
import com.wsd.core.io.Resource;


/**
 * @program: springTest
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 11:03
 **/
public class ClassPathXmlApplicationContext extends DefaultResourceLoader {

    private String[] configLocations;

    /** Bean factory for this context. */
    private volatile DefaultListableBeanFactory beanFactory;

    private Resource[] configResources;

    /**
     * Create a new ClassPathXmlApplicationContext, loading the definitions
     * from the given XML file and automatically refreshing the context.
     * @param configLocations resource location
     */
    public ClassPathXmlApplicationContext(String configLocations)  {

        //将配置文件的位置设置给configLocations field
        setConfigLocations(configLocations);

        //创建容器
        refresh();

    }

    /**
     * Finish the initialization of this context's bean factory,
     * initializing all remaining singleton beans.
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

        // Instantiate all remaining (non-lazy-init) singletons.
        beanFactory.preInstantiateSingletons();
    }

    public void refresh(){
        //获取一个新的、可配置的Bean工厂
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Instantiate all remaining (non-lazy-init) singletons.
        finishBeanFactoryInitialization(beanFactory);
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        //刷新BeanFactory
        refreshBeanFactory();
        //获取BeanFactory
        return getBeanFactory();
    }

    public final ConfigurableListableBeanFactory getBeanFactory() {
        DefaultListableBeanFactory beanFactory = this.beanFactory;
        if (beanFactory == null) {
            throw new IllegalStateException("BeanFactory not initialized or already closed - " +
                    "call 'refresh' before accessing beans via the ApplicationContext");
        }
        return beanFactory;
    }


    /**
     * initializing a fresh bean factory for the context's .
     */
    protected final void refreshBeanFactory(){
        //创建 beanFactory
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        // 加载Bean定义，将XML配置文件中的Bean定义加载到Bean工厂中
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * Loads the bean definitions via an XmlBeanDefinitionReader.
     */
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)  {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        // Configure the bean definition reader with this context's
        // resource loading environment.
        //beanDefinitionReader.setEnvironment(this.getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        //beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        //initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)  {

        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }




    protected String[] getConfigLocations() {
        return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
    }

    protected String[] getDefaultConfigLocations() {
        return null;
    }


    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * Set the config locations for this application context.
     * If not set, the implementation may use a default as appropriate.
     */
    public void setConfigLocations(String... locations) {
        if (locations != null) {
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = locations[i].trim();
            }
        }
        else {
            this.configLocations = null;
        }
    }

    public Object getBean(String beanName){
        Object bean = null;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)getBeanFactory();
        return beanFactory.getSingletonObjects().get(beanName);
    }

}

