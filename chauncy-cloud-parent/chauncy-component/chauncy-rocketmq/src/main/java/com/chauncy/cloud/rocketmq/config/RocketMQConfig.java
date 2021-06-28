package com.chauncy.cloud.rocketmq.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class RocketMQConfig {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    private void init(){
        RocketMQProducer.mqTemplate = rocketMQTemplate;
    }
}
