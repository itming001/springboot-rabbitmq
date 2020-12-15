package com.li.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lym
 */
@Configuration
public class RabbitConfig {
    /**
     * 绑定键MAN
     */
    public final static String MAN = "topic.man";
    /**
     * 绑定键WOMAN
     */
    public final static String WOMAN = "topic.woman";

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue testTopicQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("testTopicQueue",true);
    }

    /**
     * 交换机
     * @return
     */
    @Bean
    public TopicExchange testTopicExchange(){
      return new TopicExchange("testTopicExchange",true,false);
    }

    /**
     * 将队列和交换机进行绑定
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(){
        return BindingBuilder.bind(testTopicQueue()).to(testTopicExchange()).with(MAN);
    }

    /**
     * 配置消息回调
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        connectionFactory.isPublisherReturns();
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //发送交换机成功、失败会触发此回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
            System.out.println("ConfirmCallback:     "+"确认情况："+ack);
            System.out.println("ConfirmCallback:     "+"原因："+cause);
        });
        //发送队列失败会触发此函数
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("==================="+returnedMessage);
        });

        return rabbitTemplate;
    }

}
