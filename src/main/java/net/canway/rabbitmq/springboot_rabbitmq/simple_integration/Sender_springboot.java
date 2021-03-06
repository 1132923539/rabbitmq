package net.canway.rabbitmq.springboot_rabbitmq.simple_integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender_springboot {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        for (int i = 0; i < 50; i++) {

            String context = new Date().toLocaleString() + "  hello rabbitmq intagrat springboot--: " + i;

            //这里需要制定路由名称
            this.rabbitTemplate.convertAndSend("simple-springboot-rabbit", context);
        }
        System.out.println("消息：----------------- 发送成功---------");
    }

}
