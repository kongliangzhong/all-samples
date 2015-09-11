package cn.klzhong.spring.scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("file:/opt/klzhong/gaia/spring/spring-scheduling/config.properties")
@ComponentScan(basePackages = "cn.klzhong.spring.scheduling")
@EnableScheduling
public class Main {

    public static void main(String[] args) throws Exception {
        //annotationConfTest();
        new XmlConfiguredScheduling().test();
    }

    private static void annotationConfTest() {
        System.out.println("application starting...");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        //ScheduleTask task = ctx.getBean(ScheduleTask.class);
        System.out.println("application started");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
