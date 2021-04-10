package com.yc.biz;

public class StudentBizimpl implements StudentBiz{
    @Override
    public int add(String name) {
        System.out.println("调用了studentBizimpl中的add"+name);
        return 100;
    }

    @Override
    public void update(String name) {
        System.out.println("调用了studentBizimpl中的update"+name);
    }

    @Override
    public void find(String name) {
        System.out.println("调用了studentBizimpl中的find"+name);
    }
}
