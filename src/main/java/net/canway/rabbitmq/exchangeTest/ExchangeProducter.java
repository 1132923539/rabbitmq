package net.canway.rabbitmq.exchangeTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.canway.rabbitmq.rabbit.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class ExchangeProducter {

    private final static String EXCHANGE_NAME="test_exchange";


    @Test
    public void exProducter() throws IOException, TimeoutException {

        //连接到rabbitmq
        Connection connection=ConnectionUtil.getConnet();
        Channel channel=connection.createChannel();

        /*
         * 声明exchange(交换机)
         * 参数1：交换机名称
         * 参数2：交换机类型,交换机有四种类型direct，fanout，headers，topic
         * 参数3：交换机持久性，如果为true则服务器重启时不会丢失
         * 参数4：交换机在不被使用时是否删除
         * 参数5：交换机的其他属性
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout",true,true,null);


        for (int i = 0; i < 50; i++) {
            //消息内容
            String message=new Date().toLocaleString()+ ":Hello Exchange! - "+i;
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        }
        System.out.println("消息发送到交换机成功！");



        channel.close();
        connection.close();
    }
}
