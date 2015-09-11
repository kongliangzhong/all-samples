package cn.klzhong.spring.amqp.objconvert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.amqp.core.AmqpTemplate;

public class Main {

    public static void main(String[] args) {
        annotationConfDemo();
        xmlConfDemo();
    }

    private static void annotationConfDemo() {
        ApplicationContext context =
            new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
        MessageObj mo = new MessageObj(1, "test1");
        template.convertAndSend("myqueue", mo);
        MessageObj recievedMo = (MessageObj) template.receiveAndConvert("myqueue");
        System.out.println("receive obj:" + recievedMo.toString());
    }

    private static void xmlConfDemo() {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:/rabbit-config.xml");
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
        MessageObj mo = new MessageObj(2, "test2");
        template.convertAndSend("myqueue", mo);
        MessageObj recievedMo = (MessageObj) template.receiveAndConvert("myqueue");
        System.out.println("receive obj:" + recievedMo.toString());
    }


    public static class MessageObj {
        private long id;
        private String name;

        public MessageObj() {}

        public MessageObj(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String toString() {
            return "{id : " + id + ", name : " + name + "}";
        }
    }

}
