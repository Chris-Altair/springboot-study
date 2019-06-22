package com.study.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiverC {
    @RabbitListener(queues = FanoutConfig.QUEUE_C)
    public void process(String message){
        System.out.println("Fanout ReceiverC : " + message);
    }
}
