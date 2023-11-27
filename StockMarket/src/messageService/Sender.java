package messageService;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static String queue_name;
    private ConnectionFactory factory;

    public Sender(String queue_name){
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
    }


}
