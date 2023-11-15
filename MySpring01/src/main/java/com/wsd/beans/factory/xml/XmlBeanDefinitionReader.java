package com.wsd.beans.factory.xml;

import com.wsd.context.support.BeanDefinitionRegistry;
import com.wsd.core.io.Resource;
import com.wsd.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 16:21
 **/
public class XmlBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }



    //加载 BeanDefinition ，返回已加载的BeanDefinition数量
    public int loadBeanDefinitions(String... locations) {

        int count = 0;
        for (String location : locations) {
            count += loadBeanDefinitions(location);
        }
        return count;
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    //解析xml文件 成为 DOM 树结构
    public Document loadDocument(InputSource inputSource){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try{
            builder = factory.newDocumentBuilder();
            doc = builder.parse(inputSource);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return doc;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    //Register the bean definitions contained in the given DOM document.
    public int registerBeanDefinitions(Document doc, Resource resource) {
        BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader(this);
        int countBefore = getRegistry().getBeanDefinitionCount();
        documentReader.registerBeanDefinitions(doc);
        return getRegistry().getBeanDefinitionCount() - countBefore;
    }

    protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) {

        Document doc = loadDocument(inputSource);
        int count = registerBeanDefinitions(doc, resource);
        return count;


    }


    public int loadBeanDefinitions(String location) {

        int count = 0;

        ResourceLoader resourceLoader = getResourceLoader();

        if (resourceLoader == null) {
            System.out.println(
                    "Cannot load bean definitions from location [" + location + "]: no ResourceLoader available");
        }

        Resource resource = resourceLoader.getResource(location);

        try (InputStream inputStream = resource.getInputStream() ) {

            InputSource inputSource = new InputSource(inputStream);

            count = doLoadBeanDefinitions(inputSource, resource);


        }
        catch (IOException ex) {
            System.out.println("Could not resolve bean definition resource pattern [" + location + "]");
            ex.printStackTrace();
        }
        return count;
    }
}
