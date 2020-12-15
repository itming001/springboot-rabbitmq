package com.li.rabbitmq.monitor;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * rabbitmq消费者
 * @author lym
 */
@Component
@RabbitListener(queues = "testTopicQueue")
public class RabbitListening {
    /**
     * rabbitmq接收生产者发送的消息
     * @param message
     */
    @RabbitHandler
    public void process(Map message){
        System.out.println(message);
    }
}
