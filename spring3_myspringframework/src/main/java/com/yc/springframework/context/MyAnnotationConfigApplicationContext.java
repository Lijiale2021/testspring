package com.yc.springframework.context;

import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:04
 */
public class MyAnnotationConfigApplicationContext implements MyApplicationContext {
    //存的是  类名 和对象实例
    private Map<String,Object> map=new HashMap<String, Object>();

    public MyAnnotationConfigApplicationContext(Class<?>...componentClasses) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException, ClassNotFoundException {
        //通过构造方法来创建容器
        register(componentClasses);
    }

    private void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        //可能是多个
        if (componentClasses.length<=0||componentClasses==null){
            return;
        }
        //可能有多个容器字节码
        for (Class cl:componentClasses){
            if (!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }
            //如果没有填则就是这个容器类的包下的所有字节码和子包
            String[] basePackages=getAppConfigBasePackages(cl);
            if (cl.isAnnotationPresent(MyComponentScan.class)){
                MyComponentScan mcs= (MyComponentScan) cl.getAnnotation(MyComponentScan.class);
                if (mcs.basePackages()!=null&&mcs.basePackages().length>0){
                    //如果有则就是填写的值如com.yc.bean
                    basePackages=mcs.basePackages();
                }
            }
            //处理@MyBean的情况
            Object obj=cl.newInstance();//就是当前解析的MyAppconfig对象
            handleAtMyBean(cl,obj);
            //处理basePackage  基础包下的所有托管bean
            for (String basePackage:basePackages){
                scanPackageAndSubPackageClasses(basePackage);//这里只负责扫描不看有没有@Component
                //com.yc.bean
            }
            
            //继续处理所有托管Bean  IOC操作完成
            handleManagedBean();
            //接下来要进行DI操作  只对beanMap中托管的bean进行注入
            handleDI(map);
            
        }

    }

    
    //map中的每一个Bean中每一个方法上有@Autowired @Resource注解的方法实现di
    private void handleDI(Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection=map.values();
        for (Object obj:objectCollection){
            Class clss=obj.getClass();
            Method[] methods=clss.getDeclaredMethods();
            for (Method m:methods){
                if (m.isAnnotationPresent(MyAutowired.class)&&m.getName().startsWith("set")){
                    invokeAutowiredMethod(m,obj);
                }else if (m.isAnnotationPresent(MyResourcce.class)&&m.getName().startsWith("set")){
                    invokeResource(m,obj);
                }
            }
            Field[] fs=clss.getDeclaredFields();
            for (Field field:fs){
                if (field.isAnnotationPresent(MyAutowired.class)){
                    
                }else if (field.isAnnotationPresent(MyResourcce.class)){
                    
                }
            }
            
        }
    }

    //激活带有Resource的方法
    private void invokeResource(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //取出Resource中的name的值 当成beanID
        MyResourcce mr=m.getAnnotation(MyResourcce.class);
        String beanID=mr.name();
        //如果name的值等于空  则取出参数类型然后把首字母小写当成beanID
        if (beanID==null||beanID.equalsIgnoreCase("")){
           String pname=m.getParameterTypes()[0].getSimpleName();//取简单名不然就是全路径
            beanID=pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        //从map中拿到实例
        Object o=map.get(beanID);
        //激活
        m.invoke(obj,o);
    }

    //激活带有Autowired的方法
    private void invokeAutowiredMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //取出m的参数类型
       Class c=m.getParameterTypes()[0];
       //从map中循环所有的object
        Set<String> keys=map.keySet();
        for (String key:keys){
            //取出所有的实例
            Object o=map.get(key);
            //判断他们的类型是否一样，如果一样则激活
           Class[] interfaces= c.getInterfaces();
           for (Class cs:interfaces){
               if (cs==c){
                   m.invoke(obj,o);
                   break;
               }
           }
        }
    }

    //处理managedbeanclasses中所有class类 筛选出所有的@Component @Service @Repository的类  并实例化 存到beanMap中
    private void handleManagedBean() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Class c:managedBeanClasses){
            if (c.isAnnotationPresent(MyConponent.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyService.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyRepository.class)){
                saveManagedBean(c);
            }else {
                continue;
            }
        }
    }

    private void saveManagedBean(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o=c.newInstance();
        handlePostConstruct(o,c);
        String beanID=c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        map.put(beanID,o);

    }

    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath=basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径:"+basePackage+",替换后:"+packagePath);
        Enumeration<URL> files=Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (files.hasMoreElements()){
            URL url=files.nextElement();
            System.out.println("配置的扫描路径为:"+url.getFile());
            findClassesInPackages(url.getFile(),basePackage);//第二个参数  com.yc.bean;

        }
    }
    private Set<Class> managedBeanClasses=new HashSet<Class>();

    //查找file 下面及自爆所有的要托管的class，存到一个set中
    private void findClassesInPackages(final String file, String basePackage) throws ClassNotFoundException {
       File files= new File(file);
       File[] classfile=files.listFiles(new FileFilter() {
           public boolean accept(File pathname) {
               return pathname.getName().endsWith(".class")||pathname.isDirectory();
           }
       });
       if (classfile.length<=0||classfile==null){
           return;
       }
       //循环所有的文件字节码得到子目录和字节码文件
       for (File cf:classfile){
           if (!cf.isDirectory()){
               //如果是字节码则加入到set中
               URL[] urls=new URL[]{};
               URLClassLoader ucl=new URLClassLoader(urls);
               Class c=ucl.loadClass(basePackage+"."+cf.getName().replaceAll(".class",""));
               managedBeanClasses.add(c);
           }else {
               //如果是子目录则循环
               basePackage+="."+cf.getName().substring(cf.getName().lastIndexOf("/")+1);
               findClassesInPackages(cf.getAbsolutePath(),basePackage);
           }
       }
    }

    private void handleAtMyBean(Class cls,Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.获取cls中所有的方法
       Method[] methods= cls.getDeclaredMethods();
       for (Method m:methods){
           //2.判断每个方法上是否有@MyBean注解
           if (m.isAnnotationPresent(MyBean.class)){
               //3.有则invoke它 它有返回值则把他存到map里面 方法名为键 返回值为值
              Object o= m.invoke(obj,null);
              //TODO:加入处理 @MyBean注解对应的方法所实例化的类中的@MyPostConstruct对应的方法
               handlePostConstruct(o,o.getClass());//o是helloworld的对象 o.getclass是helloworld反射对象
               map.put(m.getName(),o);
               handlePreDestroy(o,o.getClass());
           }
       }


    }

    private void handlePreDestroy(Object o, Class<?> aClass) {
        Method[] methods= aClass.getDeclaredMethods();
        for (Method m:methods){}
    }

    //处理
    private void handlePostConstruct(Object o, Class<?> aClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods= aClass.getDeclaredMethods();
        for (Method m:methods){
            if (m.isAnnotationPresent(MyPostConstruct.class)){
                m.invoke(o,null);
            }
        }
    }

    private String[] getAppConfigBasePackages(Class cl) {
        String[] path=new String[1];
        path[0]=cl.getPackage().getName();
        return path;

    }


    public Object getBean(String id) {
       return map.get(id);
    }
}
