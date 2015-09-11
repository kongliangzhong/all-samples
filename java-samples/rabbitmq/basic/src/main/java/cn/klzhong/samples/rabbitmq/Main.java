package cn.klzhong.samples.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        //BasicSendRecvTest.test();
        //new PublishSubscribeSample().test();
        //new RoutingSample().test();
        new TopicSample().test();
    }
}
