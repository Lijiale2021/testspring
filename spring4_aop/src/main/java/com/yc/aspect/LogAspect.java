package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-09 19:34
 */

@Aspect//切面类:  你要增强的功能写到这里
@Component//IOC注解已实现spring托管的功能
public class LogAspect {

    //切入点的声明         *表示修饰符  中间表示方法路径  (..)表示参数
    @Pointcut("execution(* com.yc.biz.Studentbiz.add(..))") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void add() {} // the pointcut signature

    @Pointcut("execution(* com.yc.biz.Studentbiz.update(..))") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void update() {} // the pointcut signature

    @Pointcut("add()||update())") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void addAndupate() {} // the pointcut signature

    //切入点表达式的语法:  ?代表出现0次或一次
    //modifiers-pattern:修饰符
    //ret-type-pattern:返回类型
    //declaring-type-pattern:
    //name-pattern:方法名
    // execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
    //                throws-pattern?)

    //增强的声明
    @Before("com.yc.aspect.LogAspect.addAndupate()")//要么写切入点表达式要么写切入点方法名
    public void log() {
       System.out.println("==========前置增强的日志=================");
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d).toString());
        System.out.println("==========前置增强结束=================");
    }

    @After("com.yc.aspect.LogAspect.addAndupate()")
    public void bye(JoinPoint jp){//spring是一个ioc容器，它可以使用di将程序运行的信息注入 joinponit
        System.out.println("===============bye==================");
        Object target=jp.getTarget();
        System.out.println("目标类:"+target);
        System.out.println("方法:"+jp.getSignature());
        Object[] objs=jp.getArgs();
        if (objs.length>=0&&objs!=null){
            for (Object o:objs){
                System.out.println("参数:"+o.getClass());
            }
        }

    }

    @Around("execution(* com.yc.biz.Studentbiz.find(..))")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();
        long end=System.currentTimeMillis();
        System.out.println("用时:"+(end-start));
        return retVal;
    }
}
