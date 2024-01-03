package com.itheima.consumer.listeners;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MqListener {

    /**
     * 1.通过Queue进行消息传递
     *
     * @param msg
     * @return: void
     * @author NulLV
     * @creat: 2024-01-01
     */
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        System.out.println("消费者收到了simple.queue的消息：【" + msg + "】");

        // 模拟故意抛出转换异常，触发消费者确认机制
        if (true) {
            // throw new MessageConversionException("故意的，消息转换异常，reject 消息会从队列删除");
            throw new RuntimeException("故意的，运行异常，nack 消息不会从队列删除");
        }
    }

    // /**
    //  * 2.多消费者同时消费消息
    //  */
    // @RabbitListener(queues = "work.queue")
    // public void listenWorkQueue1(String msg) throws InterruptedException {
    //     System.out.println(DateUtil.now() + " 消费者1 收到了 work.queue的消息：【" + msg + "】");
    //     Thread.sleep(20);
    // }
    //
    // /**
    //  * 2.多消费者同时消费消息
    //  */
    // @RabbitListener(queues = "work.queue")
    // public void listenWorkQueue2(String msg) throws InterruptedException {
    //     System.err.println(DateUtil.now() + " 消费者2 收到了 work.queue的消息...... ：【" + msg + "】");
    //     Thread.sleep(200);
    // }
    //
    // /**
    //  * 3.广播型fanout 交换机
    //  */
    // @RabbitListener(queues = "fanout.queue1")
    // public void listenFanoutQueue1(String msg) throws InterruptedException {
    //     System.out.println(DateUtil.now() + " 消费者1 收到了 fanout.queue1的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 3.广播型fanout 交换机
    //  */
    // @RabbitListener(queues = "fanout.queue2")
    // public void listenFanoutQueue2(String msg) throws InterruptedException {
    //     System.out.println(DateUtil.now() + " 消费者2 收到了 fanout.queue2的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 3.广播型fanout 交换机
    //  */
    // @RabbitListener(queues = "resource.create.queue1.fanout")
    // public void listenFanoutQueue3(String msg) throws InterruptedException {
    //     System.out.println(DateUtil.now() + " 消费者1 收到了 resource.create.queue1.fanout 的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 3.广播型fanout 交换机
    //  */
    // @RabbitListener(queues = "resource.create.queue2.fanout")
    // public void listenFanoutQueue4(String msg) throws InterruptedException {
    //     System.out.println(DateUtil.now() + " 消费者2 收到了 resource.create.queue2.fanout 的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 4.Direct 交换机(通过注解方式创建exchange和queue)
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "direct.queue1", durable = "true"),
    //         exchange = @Exchange(name = "nullv.direct", type = ExchangeTypes.DIRECT),
    //         key = {"ovo", "qaq"}
    // ))
    // public void listenDirectQueue1(String msg) throws InterruptedException {
    //     System.out.println("消费者1 收到了 direct.queue1的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 4.Direct 交换机(通过注解方式创建exchange和queue)
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "direct.queue2", durable = "true"),
    //         exchange = @Exchange(name = "nullv.direct", type = ExchangeTypes.DIRECT),
    //         key = {"ovo", "owo"}
    // ))
    // public void listenDirectQueue2(String msg) throws InterruptedException {
    //     System.out.println("消费者2 收到了 direct.queue2的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 4.Direct 交换机(声明为lazy队列)
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "direct.queue3", durable = "true"),
    //         exchange = @Exchange(name = "nullv.direct", type = ExchangeTypes.DIRECT),
    //         key = {"wow"}
    // ))
    // public void listenDirectQueue3(String msg) throws InterruptedException {
    //     System.out.println("消费者2 收到了 direct.queue2的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 5.Topic 交换机
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "topic.queue1", durable = "true"),
    //         exchange = @Exchange(name = "nullv.topic", type = ExchangeTypes.TOPIC),
    //         key = {"china.#"}
    // ))
    // public void listenTopicQueue1(String msg) throws InterruptedException {
    //     System.out.println("消费者1 收到了 topic.queue1的消息：【" + msg + "】");
    // }
    //
    // /**
    //  * 5.Topic 交换机
    //  */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "topic.queue2", durable = "true"),
    //         exchange = @Exchange(name = "nullv.topic", type = ExchangeTypes.TOPIC),
    //         key = {"#.weather"}
    // ))
    // public void listenTopicQueue2(String msg) throws InterruptedException {
    //     System.out.println("消费者2 收到了 topic.queue2的消息：【" + msg + "】");
    // }

    /**
     * 传输对象序列化问题
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "object.queue", durable = "true"),
            exchange = @Exchange(name = "nullv.object.topic", type = ExchangeTypes.TOPIC),
            key = {"#.#"}
    ))
    public void listenObject(Map<String, Object> msg) throws InterruptedException {
        System.out.println("消费者 收到了 object.queue的消息：【" + msg + "】");
    }
}
