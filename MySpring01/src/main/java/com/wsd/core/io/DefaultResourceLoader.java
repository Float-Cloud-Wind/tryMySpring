package com.wsd.core.io;

import java.net.MalformedURLException;


/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 20:30
 **/
public class DefaultResourceLoader implements ResourceLoader {

    public Resource getResource(String location,ClassLoader classLoader) throws MalformedURLException {


        Resource resource = new ClassPathResource(location,classLoader);

        return resource;

    }

    public Resource getResource(String location) {
        return new ClassPathResource(location,null);
    }
}
