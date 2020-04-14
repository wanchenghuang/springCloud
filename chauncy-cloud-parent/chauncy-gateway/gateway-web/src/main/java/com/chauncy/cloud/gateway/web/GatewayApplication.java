package com.chauncy.cloud.gateway.web;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author cheng
 * @create 2020-03-28 14:16
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
//@MapperScan("com.chauncy.cloud.*.mapper.*")
//@ComponentScan(basePackages = {"com.chauncy.cloud"})
//@EnableSwagger2
//@EnableSwaggerBootstrapUI
@EnableDiscoveryClient
@EnableCircuitBreaker //在SpringCloud中使用断路器
//http://localhost:9004/actuator/gateway/routes
@Slf4j
@EnableMethodCache(basePackages = "com.chauncy.cloud.client")
@EnableCreateCacheAnnotation
public class GatewayApplication {

    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class,args);
        log.info("http://localhost:9004/actuator/gateway/routes");
    }
}
