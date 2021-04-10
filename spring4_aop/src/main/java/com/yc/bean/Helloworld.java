package com.yc.bean;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:19
 */

//@Component
public class Helloworld {

    @PostConstruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }



    @PreDestroy
    public void destroy(){
        System.out.println("MyPreDestroy");
    }

    public Helloworld() {
        System.out.println("无参构造方法");
    }

    public void show(){
        System.out.println("show");
    }
}
