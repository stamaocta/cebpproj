package messageService;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class TradingTape implements Runnable {

    private String queue_name;
    private Channel channel;
    private ArrayList<String> messagesList = new ArrayList<String>();

    public TradingTape(String queue_name) throws IOException, TimeoutException {
        this.queue_name = queue_name;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
    }


    @Override
    public void run() {
        while (true) {
            //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [ " + queue_name + " ] Received '" + message + "'");
                messagesList.add(message);
            };
            try {
                channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

