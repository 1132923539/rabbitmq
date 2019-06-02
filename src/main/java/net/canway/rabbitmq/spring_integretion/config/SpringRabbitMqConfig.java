package net.canway.rabbitmq.spring_integretion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;

@Configuration
public class SpringRabbitMqConfig {

    @Bean
    public RabbitAdmin getRabitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("192.168.170.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("soda4");
        connectionFactory.setUsername("soda");
        connectionFactory.setPassword("soda");
        return connectionFactory;
    }

    @Bean("topicExchange001")
    public TopicExchange topicExchange001(){
        return new TopicExchange("topic001.exchange",false,false);
    }

    @Bean("queue001")
    public Queue queue001(){
        return new Queue("queue001",false,false,false,null);
    }

    @Bean
    public Binding binding001(TopicExchange topicExchange001,Queue queue001){
        return BindingBuilder.bind(queue001).to(topicExchange001).with("spring.routing.*");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){

        return new RabbitTemplate(connectionFactory);
    }

    /**
     * 创建一个消息监听器
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001());
        /**
         * 设置每个MessageListenerContainer将会创建的Consumer的最小数量，默认是1个。
         * 若只有1个，一旦该consumer发生阻塞，将导致mq的消息无人消费，造成吞吐量下降
         *
         * 原理：设置并发数为10，即每个container会创建10个 BlockingQueueConsumer对象，
         * 每个BlockingQueueConsumer 内部维护自己的一个阻塞队列，将每次从broker中取出
         * 的消息放在这个阻塞队列中，
         */
        container.setConcurrentConsumers(10);
        //设置每个MessageListenerContainer将会创建的Consumer的最大数量，默认等于最小数量。
        container.setMaxConcurrentConsumers(20);
        //每次从broker中取出5条消息
        container.setPrefetchCount(5);
        //是否开启重回队列
        container.setDefaultRequeueRejected(false);
        //设置签收模式。这里是自动签收
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置标签策略，即在管控台能看见的channel的名称
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue+"--自定义标签";
            }
        });

        // 适配器模式，指定一个适配器对象，与适配器的方法，MessageListenerAdapter 会去调用这个适配器中的该方法
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMessageDelegate());
        adapter.setDefaultListenerMethod("handleMessage");
        //给适配器设置消息转换器
        adapter.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper()));
        //使用适配器来实现消费消息
        container.setMessageListener(adapter);


//        //设置消费方式
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println(new String(message.getBody()));
//            }
//        });

        return container;
    }

}
