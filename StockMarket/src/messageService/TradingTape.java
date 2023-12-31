package messageService;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;

public class TradingTape<T> implements Runnable {

    private String queue_name;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    String currentColor;
    private Class<T> typeParameterClass;
    private Channel channel;
    private ArrayList<T> messagesList = new ArrayList<>();
    private Gson gson = new Gson();
    long end=System.currentTimeMillis()+30000;

    public TradingTape(Class<T> typeParameterClass,String queue_name) throws IOException, TimeoutException {
        this.queue_name = queue_name;
        this.typeParameterClass=typeParameterClass;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        if (queue_name == "Buy Channel") currentColor = ANSI_RED;
        if (queue_name == "Sell Channel") currentColor = ANSI_GREEN;
        if (queue_name == "Transaction Channel") currentColor = ANSI_CYAN;
    }

    public void printMessages(){
        System.out.println(queue_name+" List:\n"+messagesList);
    }

    @Override
    public void run() {
        while (System.currentTimeMillis()<=end) {
            //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [ "+ currentColor + queue_name + ANSI_RESET +" ] Received '" + message + "'");
                T object =gson.fromJson(message, typeParameterClass);
                messagesList.add(object);
            };
            try {
                channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //to uncomment if you want to see list of messages(after 30s)
        //printMessages();
    }

}

