package com.yc.dao;

import com.yc.springframework.stereotype.MyRepository;

import java.util.Random;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-04 14:46
 */
@MyRepository
public class Studentdaojpa implements Studentdao {
    public int add(String name) {
        System.out.println("jpa添加成功"+name);
        Random r=new Random();
        return r.nextInt();
    }

    public void update(String name) {
        System.out.println("jpa修改成功"+name);
    }
}
