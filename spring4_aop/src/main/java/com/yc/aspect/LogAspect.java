package com.yc.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-09 20:23
 */

@Aspect  //切面类 你要增强的功能写到这里
@Component //IOC注解 以实现让spring托管的功能
public class LogAspect {
    //切入点的声明 pointcut signature
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")
    private void add(){

    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))")
    private void update(){

    }

    @Pointcut("add()||update()")
    private void addAndUpdate(){

    }

    //切入点表达式的预防 ？代表出现0或1次
    //modifiers-pattern:修饰街
    //ret-type-pattern:返回类型
    //declaring-type-pattern:
    //name-pattern
    //erecution(modifiers-pattern? ret-type-patten declaring-type-pattern?name-pattern(param throws-pattern?))


    // h增 强的声明
    @Before("com.yc.aspect.LogAspect.add()")
    public void log(){
        System.out.println("前置增强日志================");
    }
}

