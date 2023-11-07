import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual Offer object with all the data.

public final class Screen implements Runnable{
    public static ConcurrentHashMap<Integer, Offer> offers = new ConcurrentHashMap<Integer, Offer>();

    public Screen() {
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

        for(Integer i: offers.keySet()){
            Offer offer1=offers.get(i);

            for(Integer j:offers.keySet()){
                Offer offer2=offers.get(j);

                if(offer1.getResource().equals(offer2.getResource()) && offer1.isForSale()!=offer2.isForSale()){
                    System.out.println(offer1.toString() + " matched " + offer2.toString());
                    offers.remove(offer1.getID());
                    offers.remove(offer2.getID());
                    return ;
                }

            }
        }

    }
}
