package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect//切面类:  你要增强的功能写到这里
@Component//IOC注解已实现spring托管的功能
@Order(value = 122)
public class Log3Aspect {
    @Pointcut("execution(* com.yc.biz.Studentbiz.add(..))") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void add() {} // the pointcut signature

    @Pointcut("execution(* com.yc.biz.Studentbiz.update(..))") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void update() {} // the pointcut signature

    @Pointcut("add()||update())") // the pointcut expression  切入点表达式:那些方法上增加增强
    private void addAndupate() {} // the pointcut signature


    @Around("execution(* com.yc.biz.Studentbiz.find(..))")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {//这是DI操作
        System.out.println("compute1");
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();//目标类的目标方法
        long end=System.currentTimeMillis();
        System.out.println("compute1用时:"+(end-start));
        return retVal;
    }

}
