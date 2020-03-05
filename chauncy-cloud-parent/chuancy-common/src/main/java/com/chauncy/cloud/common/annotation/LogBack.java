package com.chauncy.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2020-03-05 17:09
 *
 * 为异步方法添加traceId(不通过http请求)
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogBack {
}