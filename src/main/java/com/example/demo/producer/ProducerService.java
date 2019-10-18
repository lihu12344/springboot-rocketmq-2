package com.example.demo.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendSync(){
        for (int i=0;i<100;i++){
            SendResult result=rocketMQTemplate.syncSend("topic-1:sync", MessageBuilder.withPayload("瓜田李下 sync"+i).build());
            System.out.println(result);
        }
    }

    public void sendAsync(){
        for (int i=0;i<100;i++){
            rocketMQTemplate.asyncSend("topic-1:async", "瓜田李下 async" + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功："+sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败："+throwable);
                }
            });
        }
    }

    public void sendOneWay(){
        for(int i=0;i<100;i++){
            rocketMQTemplate.sendOneWay("topic-1:oneway","瓜田李下 oneway"+i);
        }
    }

    public void sendDelay(){
        SendResult result=rocketMQTemplate.syncSend("topic-1:delay",MessageBuilder.withPayload("瓜田李下 delay").build(),2000,2);
        System.out.println("发送时间："+System.currentTimeMillis());
        System.out.println(result);
    }

    public void sendInOrder(){
        rocketMQTemplate.setMessageQueueSelector((list, message, o) -> list.get(Integer.parseInt(o.toString())));
        for(int i=0;i<100;i++){
            SendResult result=rocketMQTemplate.syncSendOrderly("topic-2:order","瓜田李下 顺序消息"+i,"1");
            System.out.println(result);
        }
    }
}