package com.yc;

import com.yc.biz.StudentBiz;
import com.yc.biz.StudentBizimpl;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        StudentBiz sbm=new StudentBizimpl();

        LogAspect la=new LogAspect(sbm);

        Object o=la.createProxy();

        if (o instanceof StudentBiz){
            StudentBiz sb=(StudentBiz)o;
            sb.add("张三");//这里就会自动回调invoke方法
        }
    }
}
