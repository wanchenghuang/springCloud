package com.chauncy.cloud.auth.authentication;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author cheng
 * @create 2020-04-01 22:42
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
@EnableDiscoveryClient
@MapperScan("com.chauncy.cloud.*.mapper.*")
@ComponentScan(basePackages = {"com.chauncy.cloud"})
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
@EnableCreateCacheAnnotation
@EnableFeignClients(basePackages = {"com.chauncy.cloud.client"})
public class Oauth2AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthenticationApplication.class,args);
        log.info("鉴权服务启动成功;http://localhost:9007/doc.html");
    }
}
