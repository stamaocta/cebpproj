package messageService;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sender {

    private static String queue_name;
    private ConnectionFactory factory;
    private static Lock lock=new ReentrantLock();

    public Sender(String queue_name){
        lock.lock();
        this.queue_name=queue_name;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void sendMessage(String message) throws IOException, TimeoutException {

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
                channel.queueDeclare(queue_name, false, false, false, null);
                channel.basicPublish("", queue_name, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[ " + queue_name +" ] Sent '" + message + "'");

            }
        lock.unlock();
    }


}
