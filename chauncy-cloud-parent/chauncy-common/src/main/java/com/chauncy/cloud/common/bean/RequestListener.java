package com.chauncy.cloud.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @Author cheng
 * @create 2020-03-06 22:26
 */
@Configuration
public class RequestListener {

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
