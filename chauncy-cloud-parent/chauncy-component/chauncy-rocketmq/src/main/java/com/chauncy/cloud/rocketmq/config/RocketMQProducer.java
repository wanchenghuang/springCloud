package com.chauncy.cloud.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public class RocketMQProducer {

    public static RocketMQTemplate mqTemplate;

    /**
     * 发送同步消息
     * @param topic 主题
     * @param body  消息
     */
    public static void send(String topic, String body){
        SendResult result = mqTemplate.syncSend(topic, MessageBuilder.withPayload(body).build());
        if (result.getSendStatus() != SendStatus.SEND_OK){
            // 可自行处理失败逻辑
            log.error("\n=======消息发送失败，topic：{}，数据：{}", topic, body);
        }
    }

    /**
     * 发送异步消息 在SendCallback中处理成功失败时的逻辑
     * @param topic     主题
     * @param body      消息
     * @param callback  异步通知消息
     */
    public static void sendAsync(String topic, String body, SendCallback callback){
        mqTemplate.asyncSend(topic, MessageBuilder.withPayload(body).build(), callback);
    }

    /**
     * 发送延时消息
     * 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @param topic      主题
     * @param body       消息
     * @param delayLevel 延迟等级
     */
    public static void sendDelay(String topic, String body, Integer delayLevel){
        SendResult result = mqTemplate.syncSend(topic, MessageBuilder.withPayload(body).build(), delayLevel);
        if (result.getSendStatus() != SendStatus.SEND_OK){
            log.error("\n=======消息发送失败，topic：{}，延迟等级：{}，数据：{}", topic, delayLevel, body);
        }
    }

    /**
     * 发送带tag的消息，格式：topic:tag，示例：order_topic:myTag
     * @param topic 主题
     * @param tag   tag
     * @param body  消息
     */
    public static void sendTag(String topic, String tag, String body){
        SendResult result = mqTemplate.syncSend(String.format("%s:%s", topic, tag), MessageBuilder.withPayload(body).build());
        if (result.getSendStatus() != SendStatus.SEND_OK){
            log.error("\n=======消息发送失败，topic：{}，tag:{}，数据：{}", topic, tag, body);
        }
    }

    /**
     * 单向(Oneway)发送，不可靠，可能存在丢数据的风险，建议在一些日志收集时使用
     * 由于在 oneway 方式发送消息时没有请求应答处理，一旦出现消息发送失败，则会因为没有重试而导致数据丢失。若数据不可丢，建议选用可靠同步或可靠异步发送方式。
     * @param topic 主题
     * @param body  消息
     */
    public static void sendOneWay(String topic, String body){
        mqTemplate.sendOneWay(topic, MessageBuilder.withPayload(body).build());
    }

}
