package com.study.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class CallbackSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        System.out.println("初始化rabbitmq callback...");
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    /**
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送成功:" + correlationData);
        } else {
            System.out.println("消息发送失败:" + cause);
        }
    }

    /**
     * ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(message.getMessageProperties().getCorrelationId() + " 发送失败");
    }
    //发送消息，不需要实现任何接口，供外部调用。
//    rabbitTemplate.send(message);   //发消息，参数类型为org.springframework.amqp.core.Message
//rabbitTemplate.convertAndSend(object); //转换并发送消息。 将参数对象转换为org.springframework.amqp.core.Message后发送
//rabbitTemplate.convertSendAndReceive(message) //转换并发送消息,且等待消息者返回响应消息。
    public void send(String msg){

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        System.out.println("开始发送消息 : " + msg);
        rabbitTemplate.convertAndSend(CallbackConfig.EXCHANGE,"key.1",msg,correlationId);
//        String response = rabbitTemplate.convertSendAndReceive(CallbackConfig.EXCHANGE, "key.1", msg, correlationId).toString();
        System.out.println("结束发送消息 : " + msg);
//        System.out.println("消费者响应 : " + response + " 消息处理完成");
    }
}