package com.chauncy.cloud.demos.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author cheng
 * @create 2020-04-06 21:42
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker//回路，否则设置超时那里不生效【fallback】
public class WebTestHystrixMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(WebTestHystrixMain8001.class,args);
    }
}
