package cn.klzhong.spring.amqp.objconvert;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;

@Configuration
public class RabbitConfiguration {
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("zhongkl");
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        JsonMessageConverter converter = new JsonMessageConverter();
        // DefaultClassMapper classMapper = new DefaultClassMapper();
        // classMapper.setDefaultType(Main.MessageObj.class);
        // converter.setClassMapper(classMapper);
        template.setQueue("myqueue");
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myqueue");
    }
}
