package stockExchange;

import messageService.Sender;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;

import static java.lang.Math.abs;

// stockExchange.Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual stockExchange.Offer object with all the data.


// use resource as key; value could vbe a collection
public final class Matchmaker implements Runnable{
    public String myTicker;
    long end=System.currentTimeMillis()+20000;


    public Matchmaker(String myTicker) {
        this.myTicker = myTicker;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(System.currentTimeMillis() < end) {
            try {
                processOffers();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    private void transaction(Offer offer1, Offer offer2, CopyOnWriteArrayList<Offer> WorkingOffers) throws IOException, TimeoutException {
        Sender transactionSender= new Sender("Transaction Channel");
        Transaction transaction=new Transaction(offer1,offer2);

        // ---- Operations to get Average Price between the 2 and the Quantity Sold ----
        int absPriceOffer1 = offer1.getSaleStatus() == Offer.saleEnum.SELL ? -1 * offer1.getPrice() : offer1.getPrice();
        int absPriceOffer2 = offer2.getSaleStatus() == Offer.saleEnum.SELL ? -1 * offer2.getPrice() : offer2.getPrice();
        int avgPrice = (absPriceOffer1 + absPriceOffer2) / 2;
        int avgPriceOffer1 = offer1.getSaleStatus() == Offer.saleEnum.SELL ? -1 * avgPrice : avgPrice;
        int avgPriceOffer2 = -1 * avgPriceOffer1;
        int quantitySold = offer1.getQuantity() > offer2.getQuantity() ? offer2.getQuantity() : offer1.getQuantity();
        // ------------------------------------------------------------------------------

        transactionSender.sendMessage(transaction);
        offer1.getParticipant().notifyTransaction(offer1.getOfferID(), avgPriceOffer1, quantitySold, offer1.getSaleStatus());
        offer2.getParticipant().notifyTransaction(offer2.getOfferID(), avgPriceOffer2, quantitySold, offer2.getSaleStatus());

        // Handle amount cases. [Buy 15] + [sell 7] should result in sell disappearing, and [buy 8] remaining etc.

        int quantityOffer1 = offer1.getQuantity(), quantityOffer2 = offer2.getQuantity();

        if(quantityOffer1 > quantityOffer2){
            WorkingOffers.remove(offer2);
            WorkingOffers.remove(offer1);
            Offer toAdd = new Offer(offer1.getOfferID(), offer1.getTicker(), offer1.getSaleStatus(),
                    offer1.getParticipant(), offer1.getPrice(), (quantityOffer1 - quantityOffer2));
            int indexToAdd = Matchmaker.findIndex(toAdd.getTicker(),toAdd.getPrice(),0);
            WorkingOffers.add(indexToAdd,toAdd);

        }else if(quantityOffer1 == quantityOffer2){
            WorkingOffers.remove(offer1);
            WorkingOffers.remove(offer2);
        }else {
            WorkingOffers.remove(offer1);
            WorkingOffers.remove(offer2);
            Offer toAdd = new Offer(offer2.getOfferID(), offer2.getTicker(), offer2.getSaleStatus(),
                    offer2.getParticipant(), offer2.getPrice(), (quantityOffer2 - quantityOffer1));
            int indexToAdd = Matchmaker.findIndex(toAdd.getTicker(),toAdd.getPrice(),0);
            WorkingOffers.add(indexToAdd,toAdd);

        }
    }

    public void processOffers() throws IOException, TimeoutException {

        CopyOnWriteArrayList<Offer> WorkingOffers = Screen.offers.get(myTicker);
        Offer offer2;
        int index;
        for(Offer offer1 : WorkingOffers) {
            if (offer1.getStaleness() == 0) {
                offer1.setTolerance(10);
                offer1.setStaleness();
            }
            else {
                index = findIndex(myTicker, -1 * offer1.getPrice(), offer1.getTolerance());
                if (index < 0 ) index = 0;
                if( index > WorkingOffers.size() - 1) index = WorkingOffers.size() - 1;
                offer2 = WorkingOffers.get(index);
                if (offer1.matches(offer2)) {
                    transaction(offer1, offer2, WorkingOffers);

                    // And because we want changes to reflect...
                    return;
                }
            }
        }
    }

    public static int findIndex(String ticker, int price, int tolerance) {

        CopyOnWriteArrayList<Offer> currentRow = Screen.offers.get(ticker);
        int rightBoundary = currentRow.size() - 1;
        int leftBoundary = 0;
        int middle;

        while (leftBoundary <= rightBoundary) {

            middle = leftBoundary + (rightBoundary - leftBoundary) / 2;

            if ((price - tolerance) == currentRow.get(middle).getPrice()) {
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
