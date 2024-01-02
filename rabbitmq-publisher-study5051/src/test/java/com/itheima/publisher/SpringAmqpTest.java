package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 1.通过Queue进行消息传递
     */
    @Test
    void testSendMessage2Queue() {
        String queueName = "simple.queue";
        String msg = "鸽们直接用Queue通信哈";
        rabbitTemplate.convertAndSend(queueName, msg);
    }

    /**
     * 2.无exchange，多消费者同时消费消息
     */
    @Test
    void testWorkQueue() throws InterruptedException {
        String queueName = "work.queue";
        for (int i = 1; i <= 50; i++) {
            String msg = "鸽们直接一直朝消息队列发送消息_" + i;
            rabbitTemplate.convertAndSend(queueName, msg);
            Thread.sleep(20);
        }
    }

    /**
     * 3.广播型fanout 交换机
     */
    @Test
    void testSendFanout() {
        String exchangeName = "nullv.fanout";
        String msg = "哥们直接发送给交换机 " + exchangeName;
        rabbitTemplate.convertAndSend(exchangeName, null, msg);
    }

    /**
     * 3.广播型fanout 交换机
     */
    @Test
    void testSendFanout1() {
        String exchangeName = "resource.create.exchange.fanout";
        String msg = "哥们直接发送给交换机，但是这个绑定关系是用Bean方式创建的哦 " + exchangeName;
        rabbitTemplate.convertAndSend(exchangeName, null, msg);
    }


    /**
     * 4.Direct 交换机(通过注解方式创建exchange和queue)
     */
    @Test
    void testSendDirect() {
        String exchangeName = "nullv.direct";
        String msg = "哥们给 QAQ 的消息";
        rabbitTemplate.convertAndSend(exchangeName, "qaq", msg);
    }

    /**
     * 5.Topic 交换机
     */
    @Test
    void testSendTopic() {
        String exchangeName = "nullv.topic";
        String msg = "今天会下雨";
        rabbitTemplate.convertAndSend(exchangeName, "china.OvO.weather", msg);
    }

    /**
     * 传输对象序列化问题
     */
    @Test
    void testSendObject() {
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("name", "嘻嘻嘻");
        msg.put("age", 21);
        rabbitTemplate.convertAndSend("object.queue", msg);
    }

    /**
     * 生产者确认
     */
    @Test
    void testPublisherConfirm() throws InterruptedException {
        // 1.创建CorrelationData，配置JSON序列化的Bean中已经生成了UUID，所以直接初始化对象
        CorrelationData cd = new CorrelationData();
        // 1.消息ID,确认机制发送消息时，需要给每个消息设置一个全局唯一id，以区分不同消息，避免ack冲突
        // CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 2.给Future添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                // 2.1.Future发生异常时的处理逻辑，基本不会触发
                log.error("send message fail", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                // 2.2.Future接收到回执的处理逻辑，参数中的result就是回执内容
                if (result.isAck()) { // result.isAck()，boolean类型，true代表ack回执，false 代表 nack回执
                    log.debug("发送消息成功，收到 ack!");
                } else { // result.getReason()，String类型，返回nack时的异常描述
                    log.error("发送消息失败，收到 nack, reason : {}", result.getReason());
                }
            }
        });

        // 3.发送消息
        rabbitTemplate.convertAndSend("nullv.direct", "qaq1", "嘻嘻嘻嘻", cd);

        // 稍作等待消息回执
        Thread.sleep(3000L);
    }

    /**
     * 发送非持久的消息
     */
    @Test
    void testPageOut() {
        Message message = MessageBuilder
                .withBody("hello".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.convertAndSend("nullv.direct", "wow", message);
        }
    }
}
