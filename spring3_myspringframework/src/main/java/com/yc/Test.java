package com.yc;

import com.yc.bean.Helloworld;
import com.yc.biz.Studentbiz;
import com.yc.springframework.context.MyAnnotationConfigApplicationContext;
import com.yc.springframework.context.MyApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:21
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ClassNotFoundException {
        MyApplicationContext ac=new MyAnnotationConfigApplicationContext(MyAppConfig.class);
        Studentbiz hw= (Studentbiz) ac.getBean("studentbiz");
        hw.add("张三");
    }
}
