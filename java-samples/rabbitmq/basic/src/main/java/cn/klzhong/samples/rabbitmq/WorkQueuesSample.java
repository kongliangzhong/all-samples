package cn.klzhong.samples.rabbitmq;

import java.io.IOException;
import com.rabbitmq.client.*;
//import com.rabbitmq.client.ConnectionFactory;
// import com.rabbitmq.client.Connection;
// import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.MessageProperties;

public class WorkQueuesSample {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public void test() throws Exception {
        new Thread(){
            @Override
            public void run() {
                System.out.println("sending message...");
                try {
                    send();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        Thread.currentThread().sleep(1000);

        new Thread(){
            @Override
            public void run() {
                System.out.println("recieving message...");
                try {
                    worker("W1");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("recieving message...");
                try {
                    worker("W2");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    private void send() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //queueDeclare(queue, durable, exclusive, autoDelete, Map<String,Object> args)
        //Declare a queue
        channel.queueDeclare(TASK_QUEUE_NAME, true/* durable*/, false, false, null);

        String[] messages = new String[]{"message-1", "message-2", "message-3", "message-4"};

        for (String m : messages) {
            channel.basicPublish( "", TASK_QUEUE_NAME,
                                  MessageProperties.PERSISTENT_TEXT_PLAIN,
                                  m.getBytes());
            System.out.println(" [x] Sent '" + m + "'");

        }

        channel.close();
        connection.close();
    }

    private void worker(final String name) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("zhongkl");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //basicQos(int prefetchCount)
        channel.basicQos(2);

        final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [" + name + "] Received '" + message + "'");
                }
            };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

}
