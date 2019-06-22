package com.study.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiverB {
    @RabbitListener(queues = TopicConfig.QUEUE_B)
    public void process(String message){
        System.out.println("Topic ReceiverB:"+"message = [" + message + "]");
    }
}
