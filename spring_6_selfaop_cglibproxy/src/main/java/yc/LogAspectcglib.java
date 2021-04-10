package yc;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.invoke.MethodHandleInfo;
import java.lang.reflect.Method;

public class LogAspectcglib implements MethodInterceptor {
    private Object target;

    public LogAspectcglib(Object target){
        this.target=target;
    }

    public Object createProxy(){
        //创建
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理类对象"+o.getClass());
        System.out.println("目标类的方法"+method);
        System.out.println("目标方法参数"+objects);
        System.out.println("要代理的方法"+methodProxy);

       Object returnvalue= method.invoke(this.target,objects);
        return returnvalue;
    }
}
