package com.yc;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:05
 */
@Configuration
@ComponentScan(basePackages = {"com.yc"})
@EnableAspectJAutoProxy//启用AspectJ的注解支持
public class MyAppConfig {

    //@MyBean
    //public Helloworld hw(){
    //  return new Helloworld();
    //}

}
