package com.yc.biz;

import com.yc.MyAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyAppConfig.class)
public class StudentbizTest  {

    //@Resource(name ="studentbiz")//先按名字如果没有同名的则按类型查找bean然后注入
    @Autowired//只按类型注入
    private Student st;

    @Test
    public void testAdd() {
        st.add("张三");//如果Studentbiz没有现实一个接口则是用CGLIB代理  反之则使用JDK代理
    }

    @Test
    public void testUpdate() {
        st.update("张三");
    }

    @Test
    public void testFind() {
        st.find("张三");
    }
}