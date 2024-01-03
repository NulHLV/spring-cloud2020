package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过Bean方式进行exchange和queue的声明、创建（另一种方式为用@RabbitListener注解直接创建）
 *
 * @author NulLV
 * @creat: 2024-01-02
 */
// @Configuration
public class FanoutConfiguration {

    /**
     * 通过创建Bean的方式远程创建Exchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        // 1.通过 ExchangeBuilder 创建
        // ExchangeBuilder.fanoutExchange("resource.create.exchange.fanout").build();

        // 2.通过 new FanoutExchange 创建
        return new FanoutExchange("resource.create.exchange.fanout");
    }

    /**
     * 通过创建Bean的方式远程创建Queue
     */
    @Bean
    public Queue fanoutQueue1() {
        // 1.通过 QueueBuilder 创建
        // QueueBuilder.durable("resource.create.queue.fanout").build();

        // 2.通过 new Queue 创建
        return new Queue("resource.create.queue1.fanout");
    }

    /**
     * 通过创建Bean的方式远程创建Queue
     */
    @Bean
    public Queue fanoutQueue2() {
        // 1.通过 QueueBuilder 创建
        // QueueBuilder.durable("resource.create.queue.fanout").build();

        // 2.通过 new Queue 创建
        return new Queue("resource.create.queue2.fanout");
    }

    /**
     * 绑定exchange和queue1
     */
    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    /**
     * 绑定exchange和queue2
     */
    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }


    /**
     * 创建死信交换机
     */


}
