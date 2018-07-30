package net.canway.rabbitmq.springboot_rabbitmq.advanced_integration.topicConsumer;

import net.canway.rabbitmq.springboot_rabbitmq.advanced_integration.TopicRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = TopicRabbitConfig.TOPIC_ONE)
public class TopicConsumerOne {

    @RabbitHandler
    public void process1(String topicMessage) {
        System.out.println("---------------接收TOPIC_ONE的消息为  : " + topicMessage);
    }


}
