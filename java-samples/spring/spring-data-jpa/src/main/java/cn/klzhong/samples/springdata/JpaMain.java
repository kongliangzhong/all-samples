package cn.klzhong.samples.springdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.klzhong.samples.springdata.entity.*;
import cn.klzhong.samples.springdata.repo.*;

@Component
public class JpaMain {

    private static Logger LOG = LoggerFactory.getLogger(JpaMain.class);

    @Autowired
    private LogRepo logRepo;

    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:/spring-data-jpa.xml");

        JpaMain jpaMain = context.getBean(JpaMain.class);
        jpaMain.testJpa();
    }

    private void testJpa() {
        Log l1 = new Log();
        l1.setQueryId("queryId1");
        l1.setContent("test-log-content 1");

        Log l2 = new Log();
        l2.setQueryId("queryId2");
        l2.setContent("test-log-content 2");

        Log savedLog1 = logRepo.save(l1);
        LOG.info("savedLog1 id: {}", savedLog1.getId());

    }

}
