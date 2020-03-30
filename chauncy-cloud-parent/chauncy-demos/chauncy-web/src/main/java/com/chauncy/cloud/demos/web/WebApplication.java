package com.chauncy.cloud.demos.web;

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
 * @create 2020-03-06 11:28
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
@MapperScan("com.chauncy.cloud.*.mapper.*")
@ComponentScan(basePackages = {"com.chauncy.cloud"})
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        log.info("启动成功！！！！！");
        log.info("http://localhost:9001/doc.html");
    }
}
