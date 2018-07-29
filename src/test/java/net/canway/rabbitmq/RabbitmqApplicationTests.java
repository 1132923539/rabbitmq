package net.canway.rabbitmq;

import net.canway.rabbitmq.rabbit.Consumer;
import net.canway.rabbitmq.springboot_rabbitmq.simple_integration.Consumer_springboot;
import net.canway.rabbitmq.springboot_rabbitmq.simple_integration.Sender_springboot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private Sender_springboot sender_springboot;
    @Autowired
    private Consumer_springboot consumer_springboot;

    @Test
    public void rabbit(){

        sender_springboot.send();

    }

}
