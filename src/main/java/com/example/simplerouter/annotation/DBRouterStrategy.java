package com.example.simplerouter.annotation;

import java.lang.annotation.*;

/**
 * @author: Richerlv
 * @date: 2023/7/20 23:55
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouterStrategy {
    boolean splitTable() default false;
}
