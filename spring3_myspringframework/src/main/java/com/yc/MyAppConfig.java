package com.yc;

import com.yc.bean.Helloworld;
import com.yc.springframework.stereotype.MyBean;
import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 14:05
 */
@MyConfiguration
@MyComponentScan(basePackages = {"com.yc.dao","com.yc.biz"})
public class MyAppConfig {

    //@MyBean
    //public Helloworld hw(){
    //  return new Helloworld();
    //}

}
