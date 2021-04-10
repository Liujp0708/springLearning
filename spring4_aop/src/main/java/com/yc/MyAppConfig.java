package com.yc;

import com.yc.bean.HelloWorld;
import com.yc.springframework.stereotype.*;

/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 17:17
 */
@MyConfiguration
@MyComponentScan(basePackages =  {"com.yc.bean","com.yc.biz"})
public class MyAppConfig {
    @MyBean
    public HelloWorld hw(){
        return new HelloWorld();
    }
}
