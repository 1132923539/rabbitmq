package net.canway.rabbitmq.springboot_rabbitmq.advanced_integration.topicConsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "topic.#")
public class TopicConsumerThree {

    /**
     * 接收所有消息
     *
     * @param topicMessage
     */
    @RabbitHandler
    public void process3(String topicMessage) {
        System.out.println("++++++++++++++++++++++接收所有topic的消息为  : " + topicMessage);
    }
}
