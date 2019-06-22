package com.study.callback;

//import com.fanjc.rabbitmq.topic.TopicConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CallbackReceiverA {
    @RabbitListener(queues = CallbackConfig.QUEUE_A)
    public void process(Message message, Channel channel) throws IOException {
// 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("CallbackReceiverA: " + new String(message.getBody()));
    }
}
