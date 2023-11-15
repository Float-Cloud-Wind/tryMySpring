package com.wsd.beans.factory.xml;

import org.w3c.dom.Document;

public interface BeanDefinitionDocumentReader {

    /**
     * Read bean definitions from the given DOM document and
	 * register them with the registry in the given reader context.
    */
    void registerBeanDefinitions(Document doc);
}
