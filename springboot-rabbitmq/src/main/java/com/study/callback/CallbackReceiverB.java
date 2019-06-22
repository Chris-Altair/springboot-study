package com.study.callback;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CallbackReceiverB {
    @RabbitListener(queues = CallbackConfig.QUEUE_B)
    public void process(Message message, Channel channel) throws IOException {
        int i = 1/0;//如果处理失败的话，就不会从队列中删除消息
        System.out.println("CallbackReceiverB: " + new String(message.getBody()));
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
