package com.study.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FanoutSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "no:" + i + "--hello " + new Date();
        System.out.println("Fanout Sender : " + context);
        this.rabbitTemplate.convertAndSend(FanoutConfig.EXCHANGE,"", context);//消息发到交换器，又交换器调度消息
    }
}
