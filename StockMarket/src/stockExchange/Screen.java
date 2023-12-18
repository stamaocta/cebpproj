package stockExchange;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Screen {
    public static ConcurrentHashMap<String, CopyOnWriteArrayList<Offer>> offers = new ConcurrentHashMap<String, CopyOnWriteArrayList<Offer>>();
    public static final List<String> initTickersList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

    public Screen() {
        for(String ticker : initTickersList){
            Screen.offers.put(ticker, new CopyOnWriteArrayList<Offer>());
        }
    }
}
