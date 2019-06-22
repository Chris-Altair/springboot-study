package com.study.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiverC {
    @RabbitListener(queues = TopicConfig.QUEUE_C)
    public void process(String message){
        System.out.println("Topic ReceiverC:"+"message = [" + message + "]");
    }
}
