package com.example.simplerouter.annotation;

import java.lang.annotation.*;

/**
 * @author: Richerlv
 * @date: 2023/7/20 23:52
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    // 路由的key
    String key() default "";
}
