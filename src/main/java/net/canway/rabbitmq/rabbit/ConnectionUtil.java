package net.canway.rabbitmq.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnet() {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("192.168.229.129");

        factory.setPort(5672);

        factory.setVirtualHost("myhost");
        factory.setUsername("user");
        factory.setPassword("123");

        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
