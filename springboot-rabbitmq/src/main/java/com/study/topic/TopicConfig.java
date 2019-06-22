package com.study.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    public final static String QUEUE_A = "topic.queue.a";
    public final static String QUEUE_B = "topic.queue.b";
    public final static String QUEUE_C = "topic.queue.c";

    public final static String EXCHANGE = "topic.exchange";

    public final static String ROUTE_KEY_A = "*.rabbit.*";
    public final static String ROUTE_KEY_B = "lay.#";
    public final static String ROUTE_KEY_C = "lazy.rabbit.*";

    @Bean
    public Queue queueAt(){
        return new Queue(QUEUE_A);
    }
    @Bean
    public Queue queueBt(){
        return new Queue(QUEUE_B);
    }
    @Bean
    public Queue queueCt(){
        return new Queue(QUEUE_C);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingAt(Queue queueAt, TopicExchange topicExchange){
        return BindingBuilder.bind(queueAt).to(topicExchange).with(ROUTE_KEY_A);
    }
    @Bean
    public Binding bindingBt(Queue queueBt, TopicExchange topicExchange){
        return BindingBuilder.bind(queueBt).to(topicExchange).with(ROUTE_KEY_B);
    }
    @Bean
    public Binding bindingCt(Queue queueCt, TopicExchange topicExchange){
        return BindingBuilder.bind(queueCt).to(topicExchange).with(ROUTE_KEY_C);
    }

}
