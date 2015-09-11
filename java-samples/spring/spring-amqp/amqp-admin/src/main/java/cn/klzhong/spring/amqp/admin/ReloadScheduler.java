package cn.klzhong.spring.amqp.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReloadScheduler {

    @Autowired
    private RabbitmqAdmin rabbitmqAdmin;

    private String queuesFilePath = "/opt/upsmart/upsmart-config/hardin/queues.list";

    private Set<String> currQueueSet = new HashSet<>();

    public void run() {
        System.out.println("run invoked in ReloadScheduler. " + System.currentTimeMillis());
        Set<String> queueSet = new HashSet<>();
        try (FileReader fr = new FileReader(queuesFilePath);
             BufferedReader br = new BufferedReader(fr)) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                line = line.trim();
                if (line.length() > 0) {
                    queueSet.add(line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Set<String> newQueues = queuesNeedToAdd(queueSet);
        Set<String> delQueues = queuesNeedToDelete(queueSet);
        if (newQueues.isEmpty() && delQueues.isEmpty()) {
            System.out.println("no change in config file queues.list");
        } else {
            System.out.println("have changes in config file queues.list");
            rabbitmqAdmin.createAndDeleteQueues(newQueues, delQueues);
        }

        currQueueSet.clear();
        currQueueSet.addAll(queueSet);
    }

    private Set<String> queuesNeedToAdd(Set<String> queuesInConfig) {
        if (currQueueSet == null) return queuesInConfig;
        Set<String> union = new HashSet<>();
        union.addAll(currQueueSet);
        union.addAll(queuesInConfig);
        union.removeAll(currQueueSet);
        return union;
    }

    private Set<String> queuesNeedToDelete(Set<String> queuesInConfig) {
        if (currQueueSet == null) return new HashSet<String>();
        Set<String> union = new HashSet<>();
        union.addAll(currQueueSet);
        union.addAll(queuesInConfig);
        union.removeAll(queuesInConfig);
        return union;
    }
}
