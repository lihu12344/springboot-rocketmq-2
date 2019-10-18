package com.example.demo.producer;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendInTransaction(){
        for(int i=0;i<100;i++){
            TransactionSendResult result=rocketMQTemplate.sendMessageInTransaction("transaction",
                    "topic-3", MessageBuilder.withPayload("瓜田李下 事务消息"+i).build(), i);
            System.out.println(result);
        }
    }
}

@Component
@RocketMQTransactionListener(txProducerGroup = "transaction")
class LocalExecutor implements RocketMQLocalTransactionListener {

    private ConcurrentHashMap<Integer, RocketMQLocalTransactionState> map=new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        map.put(message.hashCode(),RocketMQLocalTransactionState.UNKNOWN);

        int i=Integer.parseInt(o.toString());
        if(i==2){
            System.out.println("本地事务出错，回滚事务消息");
            map.put(message.hashCode(),RocketMQLocalTransactionState.ROLLBACK);
        }else {
            map.put(message.hashCode(),RocketMQLocalTransactionState.COMMIT);
        }

        return map.get(message.hashCode());
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return map.get(message.hashCode());
    }
}