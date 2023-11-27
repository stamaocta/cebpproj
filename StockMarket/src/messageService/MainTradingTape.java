package messageService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainTradingTape {

    public static void main(String[] args) throws IOException, TimeoutException {
        TradingTape sellTradingTape= new TradingTape("Sell Channel");
        TradingTape buyTradingTape= new TradingTape("Buy Channel");
        TradingTape transactionTradingTape= new TradingTape("Transaction Channel");

        Thread sellTread = new Thread(sellTradingTape);
        Thread buyThread = new Thread(buyTradingTape);
        Thread transactionThread = new Thread(transactionTradingTape);

        sellTread.start();
        buyThread.start();
        transactionThread.start();

        try {
            sellTread.join();
            buyThread.join();
            transactionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
