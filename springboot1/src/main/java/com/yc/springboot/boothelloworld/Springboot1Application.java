package com.yc.springboot.boothelloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication//启动类上的启动注解

public class Springboot1Application {
    public static void main(String[] args) {
        SpringApplication.run(Springboot1Application.class, args);
    }
    //路径                                    启动这个项目用的java1.8  在LiJiaLe这台机器上运行的类路径
//c.y.s.b.Springboot1Application           : Starting Springboot1Application using Java 1.8.0_221 on LiJiaLe with PID 344 (F:\testSpring\springboot1\target\classes started by Administrator in F:\testSpring)
//c.y.s.b.Springboot1Application           : No active profile set, falling back to default profiles: default
    //这里是内嵌了一个tomcat服务器   然后初始化tomcat端口号是8080
//o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
    //启动tomcat服务器
//o.apache.catalina.core.StandardService   : Starting service [Tomcat]
    //tomcat版本号
//org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.45]
    //初始化web容器
//o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
    //web初始化耗时
//w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 901 ms
    //起一个线程池 初始化一个线程
//o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
    //表示以后的项目是在tomcat根目录下  即   只需要打http://localhost:8080/...就可以运行
//o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
//c.y.s.b.Springboot1Application           : Started Springboot1Application in 1.762 seconds (JVM running for 2.618)
//o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
//    spring容器接口
//    ApplicationContext AnnotationConfigApplicationContext
//    WebApplicationContext     spring容器
//
//    8080 (http) with context path ''
//
//
//
//    spring web->  springMVC
//
//    前端控制器  dispatcherServlet  : 前端控制器启动时，加载spring容器
//    运行时，拦截所有的请求，根据配置  分发请求 到对应的控制器

}
