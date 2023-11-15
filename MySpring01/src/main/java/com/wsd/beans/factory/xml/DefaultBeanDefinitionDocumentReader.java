package com.wsd.beans.factory.xml;

import com.wsd.beans.factory.config.BeanDefinition;
import com.wsd.beans.factory.config.BeanDefinitionHolder;
import com.wsd.beans.factory.support.AbstractBeanDefinition;
import com.wsd.beans.factory.support.GenericBeanDefinition;
import com.wsd.context.support.BeanDefinitionRegistry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-12 15:04
 **/
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    //保存 XmlBeanDefinitionReader 的引用,需要访问其 registry
    private XmlBeanDefinitionReader xmlBeanDefinitionReader;

    public static final String BEAN_ELEMENT = "bean";

    public static final String NESTED_BEANS_ELEMENT = "beans";

    public static final String ID_ATTRIBUTE = "id";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String ATTRIBUTE_DELIMITERS = ",";

    public static final String CLASS_ATTRIBUTE = "class";


    public DefaultBeanDefinitionDocumentReader(XmlBeanDefinitionReader xmlBeanDefinitionReader){
        this.xmlBeanDefinitionReader = xmlBeanDefinitionReader;
    }

    //Create a new GenericBeanDefinition for the given class name
    public static AbstractBeanDefinition createBeanDefinition(String className) {

        GenericBeanDefinition bd = new GenericBeanDefinition();

        if (className != null) {
            bd.setBeanClassName(className);
        }
        return bd;
    }


    //解析bean定义元素的方法
    public AbstractBeanDefinition parseBeanDefinitionElement(Element ele, String beanName) {

        String className = null;
        //如果 XML 元素中包含 class 属性，那么将这个属性所对应的类名赋给 className 变量，同时去除可能存在的前后空格
        if (ele.hasAttribute(CLASS_ATTRIBUTE)) {
            className = ele.getAttribute(CLASS_ATTRIBUTE).trim();
        }

        //创建一个 Bean 的定义 (BeanDefinition)，使用之前解析得到的类名
        AbstractBeanDefinition bd = createBeanDefinition(className);
        //解析 Bean 定义中的属性
        //parseBeanDefinitionAttributes(ele, beanName, bd);
        //bd.setDescription(DomUtils.getChildElementValueByTagName(ele, DESCRIPTION_ELEMENT));
        //解析元数据元素并将其应用到 BeanDefinition 中
        //parseMetaElements(ele, bd);
        //parseLookupOverrideSubElements(ele, bd.getMethodOverrides());
        //parseReplacedMethodSubElements(ele, bd.getMethodOverrides());
        //parseConstructorArgElements(ele, bd);
        //parsePropertyElements(ele, bd);
        //parseQualifierElements(ele, bd);
        //bd.setResource(this.readerContext.getResource());
        //bd.setSource(extractSource(ele));
        return bd;

    }

    //解析XML配置文件中的bean定义
    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele) {
        //获取“id”和“name”属性的值
        String id = ele.getAttribute(ID_ATTRIBUTE);
        String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);

        //保存别名
        List<String> aliases = new ArrayList<>();
        if (!nameAttr.isEmpty()) {
            String[] nameArr = nameAttr.split(ATTRIBUTE_DELIMITERS);
            aliases.addAll(Arrays.asList(nameArr));
        }

        String beanName = id;
        //如果“id”未指定且存在别名，它将使用别名列表中的第一个别名作为beanName
        if (id.isEmpty() && !aliases.isEmpty()) {
            beanName = aliases.remove(0);
        }

        //解析bean定义元素
        AbstractBeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName);
        if (beanDefinition != null) {

            String[] aliasesArray = aliases.toArray(new String[0]);
            return new BeanDefinitionHolder(beanDefinition, beanName, aliasesArray);
        }

        return null;
    }

    //Register the given bean definition with the given bean factory.
    public  void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        // Register bean definition under primary name.
        String beanName = definitionHolder.getBeanName();

        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    }

    /**
     * Process the given bean element, parsing the bean definition
     * and registering it with the registry.
     */
    protected void processBeanDefinition(Element ele) {
        //解析 <bean> 元素，返回一个 BeanDefinitionHolder 对象。BeanDefinitionHolder 包含了解析后的 bean 定义以及与之关联的元数据.
        BeanDefinitionHolder bdHolder = parseBeanDefinitionElement(ele);
        if (bdHolder != null) {
            // Register the instance.
            registerBeanDefinition(bdHolder, this.xmlBeanDefinitionReader.getRegistry());
        }
    }

    //解析 Element
    private void parseDefaultElement(Element ele) {
        String tagName = ele.getTagName();
        if ( BEAN_ELEMENT.equals( tagName ) ) {
            processBeanDefinition(ele);
        }
        else if ( NESTED_BEANS_ELEMENT.equals(tagName) ) {
            parseBeanDefinitions(ele);
        }
    }

    @Override
    public void registerBeanDefinitions(Document doc) {
        //获取XML文档的根元素
        Element root = doc.getDocumentElement();

        parseBeanDefinitions(root);
    }

    /**
     * Parse the elements at the root level in the document:
     * "bean".
     * @param root the DOM root element of the document
     */
    protected void parseBeanDefinitions(Element root) {

        //获取根元素的所有子节点
        NodeList nl = root.getChildNodes();
        //遍历
        for(int i = 0; i < nl.getLength(); i++ ){
            Node node = nl.item(i);
            if(node instanceof Element){
                //对该元素进行解析
                parseDefaultElement((Element)node);
            }
        }
    }
}
