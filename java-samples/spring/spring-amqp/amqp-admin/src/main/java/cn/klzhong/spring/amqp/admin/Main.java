package cn.klzhong.spring.amqp.admin;

import java.util.HashSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        //adminTest();
        queueAutoAddDeleteTest();
    }

    private static void adminTest() throws Exception {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:/applicationContext.xml");

        RabbitmqAdmin admin = context.getBean(RabbitmqAdmin.class);
        admin.createAndDeleteQueues(new HashSet(){{add("a1"); add("a2"); add("a3");}},
                                    new HashSet(){{add("b1"); add("b2"); }});
    }

    private static void queueAutoAddDeleteTest() {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:/applicationContext.xml");
        //context.registerShutdownHook();
    }
}
