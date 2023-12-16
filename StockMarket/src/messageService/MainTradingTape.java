package messageService;

import stockExchange.Offer;
import stockExchange.Transaction;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainTradingTape {

    public static void main(String[] args) throws IOException, TimeoutException {
        TradingTape<Offer> sellTradingTape= new TradingTape<Offer>(Offer.class,"Sell Channel");
        TradingTape<Offer> buyTradingTape= new TradingTape<Offer>(Offer.class,"Buy Channel");
        TradingTape<Transaction> transactionTradingTape= new TradingTape<Transaction>(Transaction.class,"Transaction Channel");

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
