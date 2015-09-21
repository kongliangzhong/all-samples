package cn.klzhong.samples.rabbitmq;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Random;

public class RoutingSample {

    private static final String EXCHANGE_NAME = "direct_logs";

    public void test() throws Exception {
        new Thread() {
            public void run() {
                try {
                    receiveLogsWithRoutingKey();
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
                    emitLogDirect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

    }

    private void emitLogDirect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String[] severitys = new String[]{"info", "warning", "error"};

        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int severityInd = rand.nextInt(3);
            System.out.println("severityInd: " + severityInd);
            String severity = severitys[severityInd];
            String message = "message generated at: " + System.currentTimeMillis();
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignore) { }
        }

        channel.close();
        connection.close();
    }

    private void receiveLogsWithRoutingKey() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        String[] severitys = new String[]{ "warning", "error"};

        for(String severity : severitys){
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
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
