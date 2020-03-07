package com.chauncy.cloud.core.config.advice;

import com.chauncy.cloud.common.annotation.TargetDataSource;
import com.chauncy.cloud.common.constant.Constants;
import com.chauncy.cloud.core.config.base.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author cheng
 * @create 2020-03-06 13:22
 *
 * 1. AOP可以触发数据源字符串的切换，这个没问题
 * 2. 数据源真正切换的关键是 AbstractRoutingDataSource 的 determineCurrentLookupKey() **被调用，此方法是在open connection**时触发
 * 3. 事务是在connection层面管理的，启用事务后，一个事务内部的connection是复用的，所以就算AOP切了数据源字符串，但是数据源并不会被真正修改
 *
 * 所以需要配置多事物管理器 TransactionConfig
 */
@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {

    @Pointcut("@within(com.chauncy.cloud.common.annotation.TargetDataSource) || @annotation(com.chauncy.cloud.common.annotation.TargetDataSource)")
    public void pointCut(){

    }

    @Around("pointCut() && @annotation(dataSource)")
    public Object around(ProceedingJoinPoint point,TargetDataSource dataSource)throws  Throwable{
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        TargetDataSource ds = method.getAnnotation(TargetDataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(Constants.MASTER);
            log.info("选择数据源---master");
        }else{
            DynamicDataSource.setDataSource(Constants.SLAVE1);
            log.info("选择数据源---"+ds.name());
        }

        try{
            return point.proceed();
        }finally {
            DynamicDataSource.clearDataSource();
            log.info("clear datasource");
        }
    }

    /*@Before("pointCut() && @annotation(dataSource)")
    public void doBefore(TargetDataSource dataSource){
        log.info("选择数据源---"+dataSource.value().getValue());
        DynamicDataSource.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DynamicDataSource.clear();
    }*/
}
