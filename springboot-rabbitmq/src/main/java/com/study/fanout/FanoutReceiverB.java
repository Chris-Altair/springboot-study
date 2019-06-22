package com.study.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiverB {
    @RabbitListener(queues = FanoutConfig.QUEUE_B)
    public void process(String message){
        System.out.println("Fanout ReceiverB : " + message);
    }
}
