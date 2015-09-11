package cn.klzhong.samples.rabbitmq;

import java.io.IOException;
import com.rabbitmq.client.*;

public class PublishSubscribeSample {
    private static final String HOST = "zhongkl";
    private static final String EXCHANGE_NAME = "logs";

    public void test() throws Exception {
        new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("recieving message...");
                    try {
                        recieveLogs();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

        Thread.currentThread().sleep(3000);

        new Thread( new Runnable() {
                @Override
                public void run() {
                    System.out.println("sending message to exchage...");
                    try {
                        emitLog();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    private void emitLog() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i = 0; i < 5; i++) {
            String message = getMessage(new String[]{"message: ", "ts: ", "" + System.currentTimeMillis()});

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    private void recieveLogs() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("temporary queue name 1: " + queueName);

        String queueName2 = channel.queueDeclare().getQueue();
        System.out.println("temporary queue name 2: " + queueName2);

        channel.queueBind(queueName, EXCHANGE_NAME, "");
        channel.queueBind(queueName2, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [Q1-Consumer] Received '" + message + "'");
                }
            };

        Consumer consumer2 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [Q2-Consumer] Received '" + message + "'");
                }
            };

        channel.basicConsume(queueName, true, consumer);
        channel.basicConsume(queueName2, true, consumer2);
    }

}
