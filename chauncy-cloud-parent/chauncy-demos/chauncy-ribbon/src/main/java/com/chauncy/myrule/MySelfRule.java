package com.chauncy.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cheng
 * @create 2020-04-06 16:59
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){

        return new RandomRule();//定义为随机
    }
}
