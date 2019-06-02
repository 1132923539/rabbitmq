package net.canway.rabbitmq.CustomConsumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Arrays;

public class MyConsumer extends DefaultConsumer {
    Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        System.out.println("consumerTag: " + consumerTag);
//        System.out.println("envelope: " + envelope);
//        System.out.println("properties: " + properties);
        System.out.println("接收到消息为: " + new String(body));

        //设置手动ack
        channel.basicAck(envelope.getDeliveryTag(),true);
    }
}
