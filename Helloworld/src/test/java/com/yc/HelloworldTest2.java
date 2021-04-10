package com.yc;

import com.yc.biz.Helloworld;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class})
@DependsOn("helloWorld")//表示这个测试类一定要依赖helloworld类
public class HelloworldTest2  {

    @Autowired
    private Helloworld hw;//默认情况下，所有的bean都是eager勤快加载


    @Autowired
    private Helloworld hw2;




    @Test
    public void testHello() {
        System.out.println(hw.hashCode()+"\n\n"+hw2.hashCode());

        //spring容器是单例模式
    }
}