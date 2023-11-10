import java.util.concurrent.ConcurrentHashMap;

// Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual Offer object with all the data.


// use resource as key; value could vbe a collection
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

                // resource as key; protect when adding/transactioning etc
                if(offer1.getTicker().equals(offer2.getTicker()) && offer1.isForSale()!=offer2.isForSale()){
                    System.out.println(offer1.toString() + " matched " + offer2.toString());
                    offer1.getParticipant().notifyTransaction(offer1.getOfferID());
                    offer2.getParticipant().notifyTransaction(offer2.getOfferID());
                    offers.remove(offer1.getOfferID());
                    offers.remove(offer2.getOfferID());
                    return ;
                }

            }
        }

    }
}
