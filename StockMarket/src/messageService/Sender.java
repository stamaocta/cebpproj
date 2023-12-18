package messageService;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import stockExchange.Offer;
import stockExchange.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Sender {

    private static String queue_name;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";

    String currentColor;
    private ConnectionFactory factory;
    private static Lock lock=new ReentrantLock();
    private Gson gson= new Gson();

    public Sender(String queue_name){
        lock.lock();
        this.queue_name=queue_name;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        if (queue_name == "Buy Channel") currentColor = ANSI_RED;
        if (queue_name == "Sell Channel") currentColor = ANSI_GREEN;
        if (queue_name == "Transaction Channel") currentColor = ANSI_CYAN;
    }

    public void sendMessage(Offer message) throws IOException, TimeoutException {
        String gsonMessage = gson.toJson(message);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
                channel.queueDeclare(queue_name, false, false, false, null);
                channel.basicPublish("", queue_name, null, gsonMessage.getBytes(StandardCharsets.UTF_8));
                System.out.println("[ " + currentColor + queue_name + ANSI_RESET +" ]" + " Sent:\n" +
                        "┌───┬───────┬──────┬───────────┬─────┬────────┬─────────┐\n" +
                        "│ ID│Tickers│Status│Participant│Price│Quantity│Tolerance│\n" +
                        message +
                        "\n└───┴───────┴──────┴───────────┴─────┴────────┴─────────┘");
            }
        lock.unlock();
    }

    public void sendMessage(Transaction message) throws IOException, TimeoutException {
        String gsonMessage = gson.toJson(message);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.basicPublish("", queue_name, null, gsonMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println("[ "+ ANSI_CYAN + queue_name + ANSI_RESET +" ] Sent :\n" + message + "'");
        }
        lock.unlock();
    }


}
