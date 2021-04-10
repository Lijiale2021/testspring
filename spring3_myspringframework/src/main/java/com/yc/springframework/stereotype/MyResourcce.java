package com.yc.springframework.stereotype;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @program: testSpring
 * @description:
 * @author: 作者
 * @create: 2021-04-05 11:34
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface MyResourcce {
    String name() default "";
    String lookup() default "";
    Class<?> type() default java.lang.Object.class;
}
