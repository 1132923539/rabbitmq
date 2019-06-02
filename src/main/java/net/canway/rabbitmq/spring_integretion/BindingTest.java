package net.canway.rabbitmq.spring_integretion;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class BindingTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private ApplicationContext applicationContext;


    //声明一个topic交换机，并且同时绑定队列与路由key
    @Test
    public void testTopicBinding(){
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(new Queue("test.topic.queue",false,false,true,null))
                .to(new TopicExchange("test.topic.exchange",false,false,null))
                .with("user.topic")
        );
    }

    //声明一个fanout交换机
    @Test
    public void testFanoutBinding(){
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(new Queue("test.fanout.queue",false,false,true,null))
                .to(new FanoutExchange("test.fanout.exchange",false,true))
        );
    }
}
