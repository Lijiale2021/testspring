package com.yc.dao;

import com.yc.springframework.stereotype.MyRepository;
import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-04 14:43
 */
//@MyRepository
public class Studentdaomy  implements Studentdao {
    public int add(String name) {
        System.out.println("my添加成功"+name);
        Random r=new Random();
        return r.nextInt();
    }

    public void update(String name) {
        System.out.println("my修改成功"+name);

    }
}
