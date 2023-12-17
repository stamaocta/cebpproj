package stockExchange;

import messageService.Sender;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;

// stockExchange.Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual stockExchange.Offer object with all the data.


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
        while(true) {
            try {
                processOffers();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    private static void transaction(Offer offer1, Offer offer2, CopyOnWriteArrayList<Offer> WorkingOffers) throws IOException, TimeoutException {
        Sender transactionSender= new Sender("Transaction Channel");
        Transaction transaction=new Transaction(offer1,offer2);
        transactionSender.sendMessage(transaction);
        offer1.getParticipant().notifyTransaction(offer1.getOfferID());
        offer2.getParticipant().notifyTransaction(offer2.getOfferID());

        // Handle amount cases. [Buy 15] + [sell 7] should result in sell disappearing, and [buy 8] remaining etc.

        int quantityOffer1 = offer1.getQuantity(), quantityOffer2 = offer2.getQuantity();

        if(quantityOffer1 > quantityOffer2){
            WorkingOffers.remove(offer2);
            WorkingOffers.remove(offer1);
            WorkingOffers.add(new Offer(offer1.getOfferID(), offer1.getTicker(), offer1.getSaleStatus(), offer1.getParticipant(), offer1.getPrice(), (quantityOffer1 - quantityOffer2)));

        }else if(quantityOffer1 == quantityOffer2){
            WorkingOffers.remove(offer1);
            WorkingOffers.remove(offer2);
        }else {
            WorkingOffers.remove(offer1);
            WorkingOffers.remove(offer2);
            WorkingOffers.add(new Offer(offer2.getOfferID(), offer2.getTicker(), offer2.getSaleStatus(), offer2.getParticipant(), offer2.getPrice(), (quantityOffer2 - quantityOffer1)));

        }
    }

    public static void processOffers() throws IOException, TimeoutException {

        for(String ticker: offers.keySet()){
            CopyOnWriteArrayList<Offer> WorkingOffers = offers.get(ticker);
            for(Offer offer1 : WorkingOffers)
                for(Offer offer2 : WorkingOffers)
                    if(offer1.matches(offer2)){
                        transaction(offer1, offer2, WorkingOffers);

                        // And because we want changes to reflect...
                        return;
                    }

        }
    }

    public static int findIndex(String ticker, int price) {

        CopyOnWriteArrayList<Offer> currentRow = Screen.offers.get(ticker);
        int rightBoundary = currentRow.size() - 1;
        int leftBoundary = 0;
        int middle;

        while (leftBoundary <= rightBoundary) {

            middle = leftBoundary + (rightBoundary - leftBoundary) / 2;

            if (price == currentRow.get(middle).getPrice()) {
                return middle;
            }
            if (price < currentRow.get(middle).getPrice()) {
                rightBoundary = middle - 1;
            }
            if (price > currentRow.get(middle).getPrice()) {
                leftBoundary = middle + 1;
            }
        }

        return leftBoundary;

    }
}
