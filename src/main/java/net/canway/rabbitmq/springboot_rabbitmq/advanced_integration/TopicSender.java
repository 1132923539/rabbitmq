package net.canway.rabbitmq.springboot_rabbitmq.advanced_integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    //两个消息接受者都可以收到
    public void send_one() {
        String context = "这个消息的routingkey是 topic.one";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.one", context);
    }


    //只有TopicReceiverTwo都可以收到
    public void send_two() {
        String context = "Hi, I am message two";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.two", context);
    }

}