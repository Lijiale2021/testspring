package com.yc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//jdk动态代理三大重点
// 1.有目标类的引用
// 2.有一个创建代理实例的方法createProxy()里面有Proxy.newProxyInstance()方法来创建代理实例
// 3.有一个回调方法invoke

public class LogAspect implements InvocationHandler {
    private Object target;//目标类的对象

    public LogAspect(Object target){
        this.target=target;
    }

    public Object createProxy(){
        //新建一个代理实例
        //                            第一个参数是类加载器                        第二个是得获取到目标类实现的接口       第三个是代理类对象
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
    }


    @Override//回调方法   当jvm调用目标类对象的被代理的方法时,会由jvm自动调用这个invoke
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类对象:"+proxy.getClass());
        System.out.println("目标的方法:"+method);
        System.out.println("方法的参数:"+args);

        log();//这里就可以加增强    具体哪些方法加得看切入点表达式来判断
                                //这里一定得是目标类
        Object returnValue=method.invoke(this.target,args);//激活目标类方法

        return returnValue;
    }

    private void log(){
        System.out.println("前置增强");
    }
}
