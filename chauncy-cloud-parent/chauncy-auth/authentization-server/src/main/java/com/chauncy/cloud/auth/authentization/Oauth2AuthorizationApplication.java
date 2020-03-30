package com.chauncy.cloud.auth.authentization;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
@MapperScan("com.chauncy.cloud.*.mapper.*")
@ComponentScan(basePackages = {"com.chauncy.cloud"})
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableMethodCache(basePackages = "com.chauncy.cloud")
@EnableCreateCacheAnnotation
public class Oauth2AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthorizationApplication.class, args);
        log.info("授权服务启动成功");
    }
}
