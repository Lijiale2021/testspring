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
        Enhancer enhancer=new Enhancer();//用于生成代理对象
        enhancer.setSuperclass(this.target.getClass());//设置父类
        enhancer.setCallback(this);//设置回调用对象为本身
        return enhancer.create();//创建代理对象
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
