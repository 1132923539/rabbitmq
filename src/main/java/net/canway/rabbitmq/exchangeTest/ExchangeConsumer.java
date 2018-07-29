package net.canway.rabbitmq.exchangeTest;

import com.rabbitmq.client.*;
import net.canway.rabbitmq.rabbit.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Queue;

public class ExchangeConsumer {
    private final static String EXCHANGE_NAME = "test_exchange";
    private final static String QUEUE_NAME1 = "exchange_Consumer1";
    private final static String QUEUE_NAME2 = "exchange_Consumer2";
    private final static int MESSAGE_NUM = 1;


    @Test
    public void exchangeConsumer1() throws IOException {

        //获取连接
        Connection connet = ConnectionUtil.getConnet();
        Channel channel = connet.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");

        //定义同一时刻发送的消息数目
        channel.basicQos(MESSAGE_NUM);

        while(true){
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String s = new String(body, "utf-8");
                    System.out.println(s);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };


            channel.basicConsume(QUEUE_NAME1,true,consumer);

        }
    }


    @Test
    public void exchangeConsumer2() throws IOException {

        //获取连接
        Connection connet = ConnectionUtil.getConnet();
        Channel channel = connet.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");

        //定义同一时刻发送的消息数目
        channel.basicQos(MESSAGE_NUM);

        while(true){
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String s = new String(body, "utf-8");
                    System.out.println(s);
                    //成功使用消息的应答
                    channel.basicAck(envelope.getDeliveryTag(), false);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };


            channel.basicConsume(QUEUE_NAME2,false,consumer);

        }
    }

}
