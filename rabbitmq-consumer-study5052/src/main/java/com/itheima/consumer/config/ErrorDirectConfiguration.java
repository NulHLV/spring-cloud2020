package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NulLV
 * @create 2024年01月03日
 */
@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.listener.simple.retry.enabled", havingValue = "true")
public class ErrorDirectConfiguration {

    @Bean
    public DirectExchange ErrorExchange() {
        return new DirectExchange("error.direct");
    }

    @Bean
    public Queue ErrorDirectQueue() {
        return new Queue("error.direct.queue");
    }

    @Bean
    public Binding QueueBindingExchange() {
        return BindingBuilder.bind(ErrorDirectQueue()).to(ErrorExchange()).with("error");
    }

    /**
     * 重试耗尽后，将失败消息投递到指定的交换机
     */
    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        // return new ImmediateRequeueMessageRecoverer();
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }
}