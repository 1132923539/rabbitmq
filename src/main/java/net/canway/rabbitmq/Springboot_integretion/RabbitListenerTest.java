package net.canway.rabbitmq.Springboot_integretion;

import com.rabbitmq.client.Channel;
import net.canway.rabbitmq.spring_integretion.pojo.User;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.script.Bindings;
import java.io.IOException;
import java.util.Date;

@Component
public class RabbitListenerTest {

    @RabbitListener(
            bindings = @QueueBinding(
                    key = "mq.rabbitlistener.routingkey",
                    value = @Queue(value = "mq.rabbitlistener.queue", durable = "true"),
                    exchange = @Exchange(
                            value = "mq.rabbitlistener.exchange",
                            type = ExchangeTypes.DIRECT,
                            //打开延时消费
                            delayed = "true"
                    )
            )
    )
    public void onMessage(@Payload User user, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("@RabbitListener 消费了一只User："+user);
        System.out.println("消费时间："+new Date());
        channel.basicAck(deliveryTag,false);
    }
}
