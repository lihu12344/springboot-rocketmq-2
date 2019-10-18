package com.example.demo.consumer;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "consumerGroup-2",topic = "topic-2",selectorExpression = "*",consumeMode = ConsumeMode.ORDERLY)
public class ConsumerService2 implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("消费时间："+System.currentTimeMillis());
        System.out.println(s);
    }
}