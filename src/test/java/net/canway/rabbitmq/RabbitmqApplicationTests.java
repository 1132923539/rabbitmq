package net.canway.rabbitmq;

import net.canway.rabbitmq.springboot_rabbitmq.advanced_integration.TopicSender;
import net.canway.rabbitmq.springboot_rabbitmq.simple_integration.Consumer_springboot;
import net.canway.rabbitmq.springboot_rabbitmq.simple_integration.Sender_springboot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

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

    @Autowired
    private TopicSender topicSender;

    @Test
    public void topicTest() {
        topicSender.send_one();
        System.out.println("----------------------这是分割线--------------");
        topicSender.send_two();
    }


    @Test
    public void testLru(){
        LinkedHashMap<Object,Object> linkedHashMap = new LinkedHashMap<>(16, (float) 0.75, true);

//        linkedHashMap.
    }

    @Test
    /**
     * 文件测试
     */
    public void testFiles() throws IOException {

        //读取流
        byte[] bytes = Files.readAllBytes(Paths.get("D://Desktop//", "common-notify-enterprise.zip"));
        //将流输出到文件
        Files.copy(new ByteArrayInputStream(bytes),new File("D:\\Desktop\\权限中心API\\common-notify-enterprise.zip").toPath());
    }
}
