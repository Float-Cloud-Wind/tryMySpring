package com.wsd.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 22:36
 **/
public class ClassPathResource extends AbstractFileResolvingResource {

    private final String path;

    private final String absolutePath;

    private final ClassLoader classLoader;

    private final Class<?> clazz;


    public ClassPathResource(String path, ClassLoader classLoader) {
        //Assert.checkNonNull(path, "Path must not be null");
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        this.path = path;
        this.absolutePath = path;
        this.classLoader = ( classLoader != null ? classLoader : Thread.currentThread().getContextClassLoader() );
        this.clazz = null;
    }




    public InputStream getInputStream() throws IOException {
        InputStream is;
        if (this.clazz != null) {
            is = this.clazz.getResourceAsStream(this.path);
        }
        else if (this.classLoader != null) {
            is = this.classLoader.getResourceAsStream(this.absolutePath);
        }
        else {
            is = ClassLoader.getSystemResourceAsStream(this.absolutePath);
        }
        if (is == null) {
            throw new FileNotFoundException(path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
