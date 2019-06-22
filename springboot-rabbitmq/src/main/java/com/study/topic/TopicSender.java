package com.study.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg,String routeKey){
        System.out.println("Topic Sender:"+"msg = [" + msg + "], routeKey = [" + routeKey + "]");
        this.rabbitTemplate.convertSendAndReceive(TopicConfig.EXCHANGE,routeKey,msg);
    }
}
