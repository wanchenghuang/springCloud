package com.chauncy.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2020-03-06 13:13
 *
 * @DataSource 注解,在Mapper层中使用(防止在service出现调用其他service层的方法，防止同时用到两种数据源存在冲突)
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String name() default "";

}
