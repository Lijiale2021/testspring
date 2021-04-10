package com.yc.biz;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-04 15:09
 */
@Component//只要加了这个注解，则这个类可以被spring容器托管
@Lazy//懒加载就是没有注入的时候不会调用构造方法
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)//这表示这个类可以被多次实例化
public class Helloworld {

    public Helloworld() {
        System.out.println("无参构造方法");
    }

    public void hello(){
        System.out.println("hello");
    }
}
