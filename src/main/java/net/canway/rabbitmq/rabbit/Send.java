package net.canway.rabbitmq.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "test_queue";

    @Test
    public void mySend() throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnet();

        //从连接中创建通道
        Channel channel = connection.createChannel();

        //声明(创建队列)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        int m = 1;
        //消息内容
        for (int i = 0; i < 50; i++) {
            String message = "Hello World" + m;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            m++;

        }
        System.out.println("消息发送成功");

        channel.close();
        connection.close();
    }
}
