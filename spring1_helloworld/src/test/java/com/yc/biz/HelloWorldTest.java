package com.yc.biz;
import com.yc.AppConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldTest extends TestCase {
    private ApplicationContext ac;//spring 容器

    @Override
    @Before
    public void setUp() throws Exception {
        ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testHello() {
        HelloWorld hw =(HelloWorld) ac.getBean("helloWorld");
        hw.Hello();

        HelloWorld hw2= (HelloWorld) ac.getBean("helloWorld");
        hw2.Hello();
    }
}