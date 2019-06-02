package net.canway.rabbitmq.CustomConsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.170.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("soda4");
        connectionFactory.setUsername("soda");
        connectionFactory.setPassword("soda");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //开启消息的confirm机制
        channel.confirmSelect();

        String exchange = "test_consumer_exchange";
        String routingKey = "consumer.save";

        String msg = "Hello MyConsumer msg";

        for (int i = 0; i < 5; i++) {

            HashMap<String,Object> headers = new HashMap<String,Object>(10);
            headers.put("num",i);

//            这里的deliveryMode=1代表不持久化，deliveryMode=2代表持久化
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    //这个持久化是针对消息本身设置的，也可以设置队列级别的过期时间
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    //设置本条消息的过期时间为10秒
                    .expiration("10000")
                    .headers(headers)
                    .build();
            channel.basicPublish(exchange, routingKey+"a", true, props, (msg+i).getBytes());
        }

        //添加Confirm事件，即投递成功则返回
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                System.out.println("------------confirm事件触发-----------");
                System.out.println("投递成功！----deliveryTag: "+deliveryTag+"-"+multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("------------confirm事件触发-----------");
                System.out.println("投递失败！");
            }
        });

        //添加Return事件，投递路由key不存在，则触发
        channel.addReturnListener((replyCode, replyText, exchange1, routingKey1, properties, body) -> {
            System.out.println("-----------return事件触发-----------");
            System.out.println("replyCode: "+replyCode);
            System.out.println("replyText: "+replyText);
            System.out.println("exchange: "+ exchange1);
            System.out.println("routingKey: "+ routingKey1);
            System.out.println("properties: "+properties);
            System.out.println("body: "+ new String(body));
        });
    }

}
