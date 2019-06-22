package com.study.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiverA {
    @RabbitListener(queues = TopicConfig.QUEUE_A)
    public void process(String message){
        System.out.println("Topic ReceiverA:"+"message = [" + message + "]");
    }
}
