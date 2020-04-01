package com.chauncy.cloud.gateway.web.config;

import com.chauncy.cloud.common.constant.Constants;
import com.chauncy.cloud.gateway.web.events.BusReceiver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.chauncy.cloud.common.constant.Constants.EXCHANGE_NAME;
import static com.chauncy.cloud.common.constant.Constants.ROUTING_KEY;

/**
 * @Author cheng
 * @create 2020-03-30 23:14
 */
@Configuration
@Slf4j
public class BusConfig {

    @Value("${spring.application.name}")
    private String appName;

    //队列
    @Bean
    Queue queue(){
        String queueName = new Base64UrlNamingStrategy(appName+".").generateName();
        log.info("queue name:{}", queueName);
        return new Queue(queueName,false);
    }

    //交换机
    @Bean
    TopicExchange exchange(){
        log.info("exchange:{}", EXCHANGE_NAME);
        return new TopicExchange(EXCHANGE_NAME);
    }

    //绑定
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        log.info("binding {} to {} with {}", queue, exchange, ROUTING_KEY);
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    //适配器模式处理消息接收,注册新的监听器
    @Bean
    MessageListenerAdapter messageListenerAdapter(BusReceiver busReceiver, MessageConverter messageConverter) {
        log.info("new listener");
        return new MessageListenerAdapter(busReceiver, messageConverter);
    }

    //初始化容器
    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter, Queue queue) {
        log.info("init simpleMessageListenerContainer: {}", queue.getName());
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queue.getName());
        container.setMessageListener(messageListenerAdapter);
        return container;
    }


    //序列化
    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
    }
}
