package cn.klzhong.spring.scheduling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlConfiguredScheduling {

    public void test() throws Exception {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:/application.xml");
    }

    public void run () {
        System.out.println("run in XmlConfiguredScheduling invoked.");
    }

}
