package net.canway.rabbitmq.Springboot_integretion;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.canway.rabbitmq.spring_integretion.RabbitTemplateTest;
import net.canway.rabbitmq.spring_integretion.pojo.User;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.Correlation;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.util.Date;
import java.util.HashMap;

public class Producer {

    private static final ConnectionFactory connectionFactory =RabbitTemplateTest.connectionFactory();
    private static final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

    @Test
    public void testRabbitlistener(){
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper()));
        rabbitTemplate.setExchange("mq.rabbitlistener.exchange");
        rabbitTemplate.setRoutingKey("mq.rabbitlistener.routingkey");

        rabbitTemplate.convertAndSend(new User("李四", "哈哈哈"), new MessagePostProcessor() {
            @Override
            //加入延时参数的header属性
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",5000);
                return message;
            }
        });
        System.out.println("发送时间："+new Date());

    }
}
