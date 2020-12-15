package com.li.rabbitmq.controller;

import com.li.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 测试rabbitmq发送信息
 * @author lym
 */
@RestController
public class SendController {
    /**
     * rabbitmq发送消息模板
     */
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    @GetMapping("sendManMessage")
    public String sendManMessage(){
        //获取数据信息
        Map<String, Object> date = getDate();
        //发送数据信息
        /**
         * 交换机 testTopicExchange
         * 队列 testTopicQueue
         * 路由键 topic.man
         */
        rabbitTemplate.convertAndSend("testTopicExchange", RabbitConfig.MAN, date);
        return "ok";
    }

    /**
     * 发送消息
     */
    @GetMapping("/sendWoManMessage")
    public String sendWoManMessage(){
        //获取数据信息
        Map<String, Object> date = getDate();
        //发送数据信息
        /**
         * 交换机 testTopicExchange
         * 队列 testTopicQueue
         * 路由键 topic.man
         */
        rabbitTemplate.convertAndSend("testTopicExchange", RabbitConfig.WOMAN, date);
        return "ok";
    }


    /**
     * 模拟数据信息
     * @return
     */
    public Map<String,Object>  getDate(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        return manMap;
    }
}
