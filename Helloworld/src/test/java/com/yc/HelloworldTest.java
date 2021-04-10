package com.yc;

import com.yc.biz.Helloworld;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloworldTest extends TestCase {
    private ApplicationContext ac;//spring 容器


    public void setUp() throws Exception {
        //ApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
        //AnnotationConfigApplicationContext  基于注解的配置容器类
        ac=new AnnotationConfigApplicationContext(AppConfig.class);
        ac=new ClassPathXmlApplicationContext();
        //读取AppConfig.class  ->basePackages="com.yc"->得到要扫描的路径
        //要检查这些包中的类上是否有@Component注解 .如有则实例化
        //存到一个Map<String,Object>



    }

    public void tearDown() throws Exception {
    }

    public void testHello() {
        Helloworld hw=(Helloworld)ac.getBean("helloworld");
        hw.hello();

        Helloworld hw2=(Helloworld)ac.getBean("helloworld");
        hw2.hello();

        //spring容器是单例模式
    }
}