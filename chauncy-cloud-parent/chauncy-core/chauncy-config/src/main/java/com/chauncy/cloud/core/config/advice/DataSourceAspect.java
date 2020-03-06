package com.chauncy.cloud.core.config.advice;

import com.chauncy.cloud.common.annotation.TargetDataSource;
import com.chauncy.cloud.common.enums.system.TargetDataSourceEnum;
import com.chauncy.cloud.core.config.base.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * @Author cheng
 * @create 2020-03-06 13:22
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
            DynamicDataSource.setDataSource(TargetDataSourceEnum.MASTER.getValue());
            log.info("选择数据源---master");
        }else{
            DynamicDataSource.setDataSource(ds.value().getValue());
            log.info("选择数据源---"+ds.value().getValue());
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
