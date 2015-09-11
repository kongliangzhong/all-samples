package cn.klzhong.samples.rabbitmq;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Random;

public class TopicSample {
    private static final String EXCHANGE_NAME = "topic_logs";

    public void test() throws Exception {
        new Thread() {
            public void run() {
                try {
                    receiveLogTopic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) { }

        new Thread() {
            public void run() {
                try {
                    emitLogTopic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

    }

    private void emitLogTopic() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String[] keys = new String[]{"aa.bb.cc", "xx.yy.zz", "ss.mm"};

        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            int keyInd = rand.nextInt(3);
            String key = keys[keyInd];
            String message = "message generated at: " + System.currentTimeMillis();
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
            System.out.println(" [x] Sent '" + key + "':'" + message + "'");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignore) { }
        }

        //channel.close();
        connection.close();
    }

    private void receiveLogTopic() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        String[] bindingkeys = new String[]{"aa.#", "*.yy.*"};

        for (String key : bindingkeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, key);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                }
            };
        channel.basicConsume(queueName, true, consumer);
    }

}
