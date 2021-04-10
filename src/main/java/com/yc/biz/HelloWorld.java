package com.yc.biz;


import org.springframework.stereotype.Component;

/**
 * /**
 *
 * @program: Helloworld
 * @description:
 * @author: 作者
 * @create: 2021-04-04 14:51
 */
@Component //只要家里这个注解 则这个类可以被spring容器托管
public class HelloWorld {
    public HelloWorld(){System.out.println("无构造方法");}

    public void Hello(){System.out.println("hello world");}

}
