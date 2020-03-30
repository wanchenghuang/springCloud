package com.chauncy.cloud.gateway.admin;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author cheng
 * @create 2020-03-17 22:16
 */
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
public class GatewayAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAdminApplication.class, args);
        log.info("启动成功！！！！！");
        log.info("http://localhost:9003/doc.html");
    }
}
