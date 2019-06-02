package net.canway.rabbitmq.CustomConsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class CustomConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.170.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("soda4");
        connectionFactory.setUsername("soda");
        connectionFactory.setPassword("soda");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_consumer_exchange";
        String routingKey = "consumer.save";
        String queueName = "test_consum_queue";

        channel.exchangeDeclare(exchange,"topic",true,false,null);


        HashMap<String, Object> prop = new HashMap<>();
        //通过队列参数来设置过期时间
        prop.put("x-expires","10000");
        //exclusive属性：true表示队列只能被一个consumer连接独占
        AMQP.Queue.DeclareOk queue = channel.queueDeclare(queueName, true, false, false, prop);


        channel.queueBind(queueName,exchange,routingKey);

        //关闭自动ack
        channel.basicConsume(queueName,false,new MyConsumer(channel));
        //设置限流
        channel.basicQos(0,1,false);
    }
}
