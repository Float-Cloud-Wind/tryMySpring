package com.wsd.pojo;

/**
 * @program: springTest
 * @description:
 * @author: Mr.Wang
 * @create: 2023-11-03 12:58
 **/
public class User {

    String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(){
        this.name = "罗小黑";
        System.out.println(this.name);
    }
}
