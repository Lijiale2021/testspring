package com.yc.springboot.boothelloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//控制层注解    表明以@Restful  以rest规范(请求方式：get,put,delete,json)发请求和响应
public class HelloWorld {
    @GetMapping("/hello")//请求方式为get   ,请求路径为/hello
    public String hello(@RequestParam(value = "name",defaultValue = "World")String name){
//        /hello?name=ycinfo  ->  映射到 @RestController  注解的控制层类中找   /hello 配置的方法
        return String.format("Hello %s!",name);
    }
}
