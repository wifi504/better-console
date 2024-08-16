package com.lhl.bconsole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_17:36
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface InvalidUsage {
    String reason() default "方法在此处定义为无效！";
}
