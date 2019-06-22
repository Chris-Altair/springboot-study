package com.study.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    public final static String QUEUE_A = "fanout.queue.a";
    public final static String QUEUE_B = "fanout.queue.b";
    public final static String QUEUE_C = "fanout.queue.c";

    public final static String EXCHANGE = "fanout.exchange";

    @Bean
    public Queue queueAf(){
        return new Queue(QUEUE_A);
    }//默认是持久化的

    @Bean
    public Queue queueBf(){
        return new Queue(QUEUE_B);
    }

    @Bean
    public Queue queueCf(){
        return new Queue(QUEUE_C);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }

    /**
     * 方法参数这里是queueA和fanoutExchange，会根据bean自动装配
     * @param queueAf
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingAf(Queue queueAf, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueAf).to(fanoutExchange);//队列绑定交换器
    }

    @Bean
    public Binding bindingBf(Queue queueBf, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueBf).to(fanoutExchange);
    }

    @Bean
    public Binding bindingCf(Queue queueCf, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueCf).to(fanoutExchange);
    }

}
