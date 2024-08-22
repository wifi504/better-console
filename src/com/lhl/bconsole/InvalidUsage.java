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
@interface InvalidUsage {
    /*
     *  可以在使用此注解标记时，配合使用
     *      @Deprecated(since = "0 XX组件不支持绑定更新回调")
     *  这样会在集成开发环境中获得明显的警告提示
     * */
    String reason() default "方法在此处定义为无效！";
}
