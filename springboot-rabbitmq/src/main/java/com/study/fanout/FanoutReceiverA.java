package com.study.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 不知道为什么@RabbitListener必须在方法上并且得去掉@RabbitHandler
 */
@Component
public class FanoutReceiverA {
    @RabbitListener(queues = FanoutConfig.QUEUE_A)
    public void process(String message){
        System.out.println("Fanout ReceiverA : " + message);
    }
}




