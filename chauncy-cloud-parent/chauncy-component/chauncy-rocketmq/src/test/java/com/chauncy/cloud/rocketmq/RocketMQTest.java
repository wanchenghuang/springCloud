package com.chauncy.cloud.rocketmq;

import com.chauncy.cloud.rocketmq.config.RocketMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 消息测试
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RocketMQTest {


    /**
     * 发送同步消息
     *
     */
    @Test
    public void send() {
        for (int i = 0; i < 100; i++) {
            RocketMQProducer.send("test_topic", i + "、这是我发的同步消息！");
        }
    }

    /**
     * 发送异步消息
     *
     */
    @Test
    public void sendAsync() {
        for (int i = 0; i < 100; i++) {
            RocketMQProducer.sendAsync("test_topic", i + "、这是我发的异步消息！", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    // 处理成功逻辑
                }

                @Override
                public void onException(Throwable throwable) {
                    // 处理异常逻辑
                }
            });
        }
    }

    /**
     * 发送延迟同步消息
     *
     */
    @Test
    public void sendDelay() {
        for (int i = 0; i < 100; i++) {
            RocketMQProducer.sendDelay("test_topic", i + "、这是我发的延迟消息！", 2);
        }
    }

}
