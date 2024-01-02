package com.itheima.publisher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author NulLV
 * @create 2024年01月02日
 */
@Slf4j
@Configuration
public class MqConfirmConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.error("消息发送队列失败\n" +
                        "响应码 {}\n" +
                        "失败原因 {}\n" +
                        "交换机 {}\n" +
                        "路由key {}\n" +
                        "消息 {}\n", replyCode, replyText, exchange, routingKey, message);
            }
        });

        // 配置回调 lomda表达式写法
        // rabbitTemplate.setReturnCallback((replyCode, replyText, exchange, routingKey, message) -> {
        //     log.error("消息发送队列失败\n" +
        //             "响应码 {}\n" +
        //             "失败原因 {}\n" +
        //             "交换机 {}\n" +
        //             "路由key {}\n" +
        //             "消息 {}\n", replyCode, replyText, exchange, routingKey, message);
        // });
    }
}