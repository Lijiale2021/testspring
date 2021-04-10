package com.yc.bean;

import com.yc.springframework.stereotype.MyConponent;
import com.yc.springframework.stereotype.MyPostConstruct;
import com.yc.springframework.stereotype.MyPreDestroy;

import javax.annotation.PostConstruct;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:19
 */

//@MyConponent
public class Helloworld {

    @MyPostConstruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }



    @MyPreDestroy
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
