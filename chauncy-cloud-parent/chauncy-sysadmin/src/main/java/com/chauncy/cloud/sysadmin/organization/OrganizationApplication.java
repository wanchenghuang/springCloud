package com.chauncy.cloud.sysadmin.organization;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author cheng
 * @create 2020-03-25 21:31
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
@MapperScan("com.chauncy.cloud.*.mapper.*")
@ComponentScan(basePackages = {"com.chauncy.cloud","com.chauncy.base"})
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
@EnableDiscoveryClient
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.chauncy.cloud")
public class OrganizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
        log.info("http://localhost:8010/doc.html");
    }
}
