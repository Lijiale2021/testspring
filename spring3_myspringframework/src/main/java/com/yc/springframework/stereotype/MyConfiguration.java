package com.yc.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 11:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyConponent
public @interface MyConfiguration {
    String value() default "";
}
