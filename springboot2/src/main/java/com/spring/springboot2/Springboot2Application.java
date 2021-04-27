package com.spring.springboot2;

import com.yc.starter.hellostarter.services.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class Springboot2Application {
    public static void main(String[] args) {
        SpringApplication.run(Springboot2Application.class, args);
    }

    @Resource
    private IHelloService helloService;

    @GetMapping("/hello")
    public String sayHello(){
        return helloService.say();
    }
}
