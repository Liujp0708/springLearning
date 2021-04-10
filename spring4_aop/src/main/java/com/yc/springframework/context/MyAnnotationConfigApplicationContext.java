package com.yc.springframework.context;

import com.yc.springframework.stereotype.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;


/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 16:54
 */
public class MyAnnotationConfigApplicationContext implements MyApplicationContext {
    private Map<String, Object> beanMap = new HashMap<String, Object>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClassess)  {
        try {
            register(componentClassess);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void register(Class<?>[] componentClassess) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        if (componentClassess == null || componentClassess.length <= 0) {
            throw new RuntimeException("没有指定配置类");
        }
        for (Class cl : componentClassess) {
            if (!cl.isAnnotationPresent(MyConfiguration.class)) {
                continue;
            }
            String[] basePackages = getAppConfigBasePackages(cl);
            if (!cl.isAnnotationPresent(MyConfiguration.class)) {
                MyComponentScan mcs = (MyComponentScan) cl.getAnnotation(MyComponentScan.class);
                if (mcs.basePackages() != null && mcs.basePackages().length > 0) {
                    basePackages = mcs.basePackages();
                }
            }
            //处理@MyBean的情况
            Object obj= cl.newInstance();
            handleAtMyBean(cl,obj);
            //处理basePackgages 基础包下的所有的托管bean
            for (String basePackage:basePackages){
                scanpackageAndSubPackageClasses(basePackage);
            }
        }
        bandleManagedBean();
        handleDi(beanMap);

    }

    private void handleDi(Map<String,Object>beanMap) throws InvocationTargetException, IllegalAccessException {
        Collection<Object>objectCollection =beanMap.values();
        for (Object obj :objectCollection){
            Class cls =obj.getClass();
            Method[] ms =cls.getMethods();
            for (Method m:ms){
                if (m.isAnnotationPresent(MyAutowired.class) && m.getName().startsWith("set")){
                    invokeAutowireMethod(m,obj);
                } else if (m.isAnnotationPresent(MyResource.class) && m.getName().startsWith("set")){
                    invokeAutowireMethod(m,obj);
                }
            }

        }
    }

    private void invokeAutowireMethod(Method m , Object obj) throws InvocationTargetException, IllegalAccessException {
       //1.取出 MyMyResource中的name 属性值 当成 beanId
        MyResource mr =m.getAnnotation(MyResource.class);
        String beanId =mr.name();
        //2.如果没有 则去除m方法中的参数的类型名 改成首字母小写 当初beanId
        if (beanId==null || beanId.equalsIgnoreCase("")){
            String pname = m.getParameterTypes()[0].getSimpleName();
            beanId =pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        //3.从beanMap取出
        Object o =beanMap.get(beanId);
        //4.invoke
        m.invoke(obj,o);
    }

    private void bandleManagedBean() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Class c :managedBeanClasses){
            if (c.isAnnotationPresent(MyComponent.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyService.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyRepository.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyController.class)){
                saveManagedBean(c);
            }else {
                saveManagedBean(c);
            }
        }
    }

    private void saveManagedBean(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o =c.newInstance();
        handlePostConstruct(o,c);
        String beanId =c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        beanMap.put(beanId,o);
    }

    private void scanpackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath = basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径："+basePackage+",替换后:"+packagePath);
        Enumeration<URL> files =Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (files.hasMoreElements()){
            URL url =files.nextElement();
            System.out.println("配置的扫描路径为："+url.getFile());
            //TODO:递归这些目录 查找 .class文件
            findClassesInPackages(url.getFile(),basePackage);
        }

    }
    private Set<Class> managedBeanClasses =new HashSet<Class>(); //set方法里面对象唯一 用于去重


    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f = new File(file);
        File[] classFiles =f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        for (File cf: classFiles){
            if (cf.isDirectory()){
                basePackage+="."+cf.getName().substring(cf.getName().lastIndexOf("/t")+1);
                findClassesInPackages(cf.getAbsolutePath(),basePackage);
            }else {
                URL[]urls =new URL[]{};
                URLClassLoader ucl =new URLClassLoader(urls );
                // com.yc.bean.Hellow.class -> com.yc.bean.Hellow
                Class c =ucl.loadClass(basePackage+"."+cf.getName().replace(".class",""));
                managedBeanClasses.add(c);
            }

        }
    }
    /*处理MyAppConfig配置类中的@Bean注解 完成IOC操作*/
    private void handleAtMyBean(Class cls, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.获取cls中所有 的metho
        Method[]ms =cls.getDeclaredMethods();
        //2.循环 判断 每个metho上是否有MyBean注解
        for (Method m :ms){
            if (m.isAnnotationPresent(MyBean.class)){
                Object o =m.invoke(obj);
                handlePostConstruct(o,o.getClass());
                beanMap.put(m.getName(),o);
            }
        }
    }
    /*处理一个Bean中的 @MyPostConstruct对应方法*/
    private void handlePostConstruct(Object o, Class<?> cls) throws InvocationTargetException, IllegalAccessException {
        Method[] ms=cls.getDeclaredMethods();
        for (Method m:ms){
            if (m.isAnnotationPresent(MyPostConstruct.class)){
                m.invoke(o);
            }
        }
    }

    private String[] getAppConfigBasePackages(Class cl) {
        String[] paths =new String[1];
        paths[0]=cl.getPackage().getName();
        return paths;
    }


    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }


}