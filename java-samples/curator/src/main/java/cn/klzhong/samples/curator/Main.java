package cn.klzhong.samples.curator;

import java.util.List;
import java.util.UUID;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String connectionString = "192.168.88.159:2181";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        int connectionTimeoutMs = 60000;
        int sessionTimeoutMs = 15000;

        CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(connectionString)
            .retryPolicy(retryPolicy)
            .connectionTimeoutMs(connectionTimeoutMs)
            .sessionTimeoutMs(sessionTimeoutMs)
            .build();

        // set value in path
        log.info("cf: " + client);
        client.start();

        // String path = "/curatortest";
        // setData(client, path, "aaaaaaaaaaaaaaa".getBytes());
        // String data = getData(client, path);
        // log.info("data: {}", data);

        // createNode(client, "/x1");
        // createNode(client, "/x1/dd");
        // createNode(client, "/x1/dd/ss");
        // createNode(client, "/x1/dd/ss/zz");

        // while (true) {
        //     Thread.currentThread().sleep(1000);

        // for (int i = 0; i < 10; i ++) {
        //     String s = System.currentTimeMillis() + "-" + i;
        //     setData(client, path, s.getBytes());
        //     log.info("data for path {}: {}", path, getData(client, path));
        // }
            //}

        // for (int i = 0; i < 5; i ++) {
        //     String serverId = UUID.randomUUID().toString();
        //     log.info("generate serverId: {}, server: {}", serverId, i);
        //     if (isNodeExist(client, path + "/" + i)) {
        //         log.info("node: {} exist already.", i);
        //         setData(client, path + "/" + i, serverId.getBytes());
        //     } else {
        //         createNode(client, path + "/" + i, serverId);
        //     }
        // }

        // List<String> servers = getNodes(client, path);
        // for (String server : servers) {
        //     log.info("server: {}", server);
        //     String serverPath = path + "/" + server;
        //     String id = getData(client, serverPath);
        //     log.info("serverId: {}", id);
        // }

        System.out.println("delete x1");
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/x1");
        //client.delete().guaranteed().forPath("/x1");
        System.out.println("delete x1 finished.");

        client.close();
    }

    private static boolean isNodeExist(CuratorFramework client, String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        log.info("stat: {}", stat);
        return stat != null;
    }

    private static void createNode(CuratorFramework client, String path, String value) throws Exception {

        client.create().forPath(path, value.getBytes());
    }

    private static void createNode(CuratorFramework client, String path) throws Exception {

        client.create().forPath(path);
    }

    private static void createEphemeral(CuratorFramework client, String path, String value) throws Exception
    {
        // this will create the given EPHEMERAL ZNode with the given data
        //client.start();
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes());
        //client.close();
    }

    private static void setData(CuratorFramework client, String path, byte[] payload) throws Exception {
        // set data for the given node
        //client.start();
        client.setData().forPath(path, payload);
        //client.close();
    }

    private static String getData(CuratorFramework client, String path) throws Exception {
        //client.start();
        String val = new String(client.getData().forPath(path));
        //client.close();
        return val;
    }

    private static List<String> getNodes(CuratorFramework client, String path) throws Exception {
        //client.start();
        List<String> nodes = client.getChildren().forPath(path);

        //client.close();
        return nodes;
    }

}
