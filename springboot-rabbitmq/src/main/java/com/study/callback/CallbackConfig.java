package com.study.callback;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallbackConfig {
    public final static String QUEUE_A = "callback.queue.a";
    public final static String QUEUE_B = "callback.queue.b";

    public final static String EXCHANGE = "callback.exchange";

    public final static String ROUTE_KEY_A = "key.*";
    public final static String ROUTE_KEY_B = "key.1";

    @Bean
    public Queue queueAc(){
        return new Queue(QUEUE_A);
    }
    @Bean
    public Queue queueBc(){
        return new Queue(QUEUE_B);
    }
    @Bean
    public TopicExchange topicExchangeC(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding bindingAc(Queue queueAc, TopicExchange topicExchangeC){
        return BindingBuilder.bind(queueAc).to(topicExchangeC).with(ROUTE_KEY_A);
    }

    @Bean
    public Binding bindingBc(Queue queueBc, TopicExchange topicExchangeC){
        return BindingBuilder.bind(queueBc).to(topicExchangeC).with(ROUTE_KEY_B);
    }
}
