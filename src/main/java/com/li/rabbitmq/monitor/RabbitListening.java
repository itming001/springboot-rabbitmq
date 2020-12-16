package com.li.rabbitmq.monitor;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * rabbitmq消费者
 * @author lym
 */
@Component
@RabbitListener(queues = "testTopicQueue",ackMode = "MANUAL")
public class RabbitListening{
    /**
     * rabbitmq接收生产者发送的消息
     * @param message 接收生产者发送的消息
     * @param channel 获取对象的渠道信息
     * @param tag 用于手动确认消息信息
     */
    @RabbitHandler
    public void process(Map message, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            System.out.println(message);
            channel.basicAck(tag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        System.out.println(message);
//    }
}
