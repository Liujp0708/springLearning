package com.yc.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 17:11
 */

public class HelloWorld {
    @PostConstruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("MyPreDestroy");
    }
    public HelloWorld(){
        System.out.println("hello world 构造");
    }
    public void show(){
        System.out.println("show");
    }

}
