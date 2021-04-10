package com.yc.biz;


import com.yc.dao.Studentdao;
import com.yc.dao.Studentdaojpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-04 14:47
 */
@Service
public class Studentbiz implements Student{
    @Autowired
    @Qualifier("studentdaojpa")
    private Studentdao studentdao;

    public Studentbiz(Studentdao studentdao) {
        this.studentdao = studentdao;
    }

    public Studentbiz() {
    }

    //@Inject//javax中的依赖注入，如果有多个对象(比如这里有 Studentdaojpa和Studentdaomy对象)
    //因为有多个对象可以注入,所以这里必须用@Named("studentdaojpa"),如果只有一个对象，则不需要

    //@Autowired//spring定义的//按类型注入
    //@Qualifier("studentdaojpa")//如果有多个对象(比如这里有 Studentdaojpa和Studentdaomy对象)
    //因为有多个对象可以注入,所以这里必须用@Named("studentdaojpa"),如果只有一个对象，则不需要
    //@Resource(name="studentdaojpa")//按名字注入
    /*public void setStudentdao(Studentdao studentdao) {
        this.studentdao = studentdao;
    }*/

    public int add(String name){
        System.out.println("=================业务层==================");
        System.out.println("用户是否重名");
        int resulte=studentdao.add(name);
        System.out.println("添加成功");
        return resulte;
    }

    public void update(String name){
        System.out.println("=================业务层==================");
        System.out.println("用户是否重名");
        studentdao.update(name);
        System.out.println("修改成功");
    }

    public void find(String name){
        System.out.println("=================业务层==================");
        System.out.println("查找:"+name);
        studentdao.find(name);
        System.out.println("查找成功成功");
    }

}
