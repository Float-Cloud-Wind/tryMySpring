package com.wsd;

import com.wsd.context.support.ClassPathXmlApplicationContext;
import com.wsd.pojo.User;

/**
 * @program: MySpring01
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-11 12:11
 **/
public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ClassPathXmlApplicationContext("spring.xml");

        User user = (User)classPathXmlApplicationContext.getBean("userBean");

        System.out.println("bean:" + user.getName());
    }
}
