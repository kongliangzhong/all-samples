package cn.klzhong.spring.amqp.admin;

import java.util.HashSet;
import java.util.Set;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqAdmin {

    @Autowired
    private AmqpAdmin rabbitAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private Exchange fanoutExchange;

    @Value("${rabbit.exchange.fanout}")
    private String fanoutExchangeName;

    public void createAndDeleteQueues(Set<String> createSet, Set<String> deleteSet) {
        System.out.println("amqpTemplate: " + amqpTemplate);
        System.out.println("rabbitAdmin: " + rabbitAdmin);
        System.out.println("FanoutExchange: " + fanoutExchange);
        for (String newQ : createSet) {
            System.out.println("add new queue: " + newQ);
            try {
                Queue q = new Queue(newQ);
                rabbitAdmin.declareQueue(q);
                Binding b = BindingBuilder.bind(q).to(fanoutExchange).with("").noargs();
                rabbitAdmin.declareBinding(b);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }

        for (String unusedQ : deleteSet) {
            System.out.println("delete queue: " + unusedQ);
            try {
                Queue q = new Queue(unusedQ);
                rabbitAdmin.declareQueue(q);
                Binding b = BindingBuilder.bind(q).to(fanoutExchange).with("").noargs();
                rabbitAdmin.removeBinding(b); //
                rabbitAdmin.deleteQueue(unusedQ);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }
    }

}
