package com;

import org.springframework.context.annotation.*;

import java.util.Random;


/**
 * /**
 *
 * @program: Helloworld
 * @description:
 * @author: 作者
 * @create: 2021-04-04 15:24
 */
@Configuration//表示一个配置类
@ComponentScan(basePackages ={"com.huwei","com.mimi"})//将要托管的bean扫描的包及子包
public class AppConfig {
    @Bean //beanid："r"
    public Random r(){
        return new Random();
    }
}
