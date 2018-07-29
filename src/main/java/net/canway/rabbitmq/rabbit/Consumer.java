package net.canway.rabbitmq.rabbit;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Consumer {
    private final static String QUEUE_NAME = "test_queue";
    private final static int mod = 1;

    @Test
    public void consumer1() throws IOException {
        //获取连接
        Connection connet = ConnectionUtil.getConnet();
        Channel channel = connet.createChannel();

        //声明队列，这个声明只是为了安全起见，若队列存在，则不做任何操作，不存在则创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(mod);

        while (true) {

            //定义队列的消费者
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery,有多少消息，这个回调就会执行多少次
            DefaultConsumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println(msg);

                    //由于关闭自动应答，因此这里需要手动确认消费
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };

            //第二个参数为false，表示手动应答模式，需要在handleDelivery中确认消息被成功消费
            channel.basicConsume(QUEUE_NAME, false, consumer);



        }

    }

    @Test
    public void consumer2() throws IOException {
        //获取连接
        Connection connet = ConnectionUtil.getConnet();
        Channel channel = connet.createChannel();

        //声明队列，这个声明只是为了安全起见，若队列存在，则不做任何操作，不存在则创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //同一时刻服务器发送给消费者消息的数量
        channel.basicQos(mod);

        while (true) {
            //定义队列的消费者
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery,有多少消息，这个回调就会执行多少次
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println(msg);

                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            //自动回复队列应答 -- RabbitMQ中的消息确认机制,第二个参数为true表示自动模式，false表示手动模式
            channel.basicConsume(QUEUE_NAME, true, consumer);



        }

    }

}
