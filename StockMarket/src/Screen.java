import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual Offer object with all the data.


// use resource as key; value could vbe a collection
public final class Screen implements Runnable{
    public static ConcurrentHashMap<String, CopyOnWriteArrayList<Offer>> offers = new ConcurrentHashMap<String, CopyOnWriteArrayList<Offer>>();



    public Screen() {
        List<String> initTickersList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        for(String ticker : initTickersList){
            Screen.offers.put(ticker, new CopyOnWriteArrayList<Offer>());
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(offers.size()>1) {
            matchOffers();
        }
    }

    public static void matchOffers(){


        for(String ticker: offers.keySet()){
            CopyOnWriteArrayList<Offer> tickerList = offers.get(ticker);
            for(Offer offer1 : tickerList)
                for(Offer offer2 : tickerList)
                    if(offer1.matches(offer2)){
                        System.out.println(offer1.toString() + " matched " + offer2.toString());
                        offer1.getParticipant().notifyTransaction(offer1.getOfferID());
                        offer2.getParticipant().notifyTransaction(offer2.getOfferID());
                        tickerList.remove(offer1);
                        tickerList.remove(offer2);
                        return ;
                    }

        }
    }
}
