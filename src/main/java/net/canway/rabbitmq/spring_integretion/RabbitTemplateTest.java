package net.canway.rabbitmq.spring_integretion;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.canway.rabbitmq.spring_integretion.pojo.User;
import org.junit.Test;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RabbitTemplateTest {

//    @Autowired
    private RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory());



    @Test
    public void test1() throws IOException {

        //发送端的RabbitTemplate设置转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper()));
        User u1 = new User("张三", "23");
        //设置不能被接收的消息托管，否则会被自动删除
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.convertAndSend("topic001.exchange","spring.routing.haha",u1);

    }

    public static ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("192.168.170.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("soda4");
        connectionFactory.setUsername("soda");
        connectionFactory.setPassword("soda");
        return connectionFactory;
    }
}
