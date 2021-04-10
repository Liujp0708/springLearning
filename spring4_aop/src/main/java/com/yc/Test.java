package com.yc;

import com.yc.bean.HelloWorld;
import com.yc.springframework.context.MyAnnotationConfigApplicationContext;
import com.yc.springframework.context.MyApplicationContext;

import java.lang.reflect.InvocationTargetException;

/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 17:20
 */
public class Test {
    public static void main(String[] args) throws Exception {
        MyApplicationContext ac = new MyAnnotationConfigApplicationContext(MyAppConfig.class);
        HelloWorld hw = (HelloWorld) ac.getBean("hw");
        hw.show();
    }
}
