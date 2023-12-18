package stockExchange;

import messageService.Sender;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Participant implements Runnable {
    private String participantID;
    private AtomicInteger balance = new AtomicInteger(0);

    public void printBalance() {

        String text = String.format("┌────────┬───────┐\n│BALANCE:│%7d│\n└────────┴───────┘",balance.get());
        System.out.println(participantID+ ":\n" + text);
    }


    public Participant(String participantID) {
        this.participantID = participantID;
    }


    public String getParticipantID() {
        return participantID;
    }


    public void addOffers() throws IOException, TimeoutException {
        Random rand = new Random();
        long end=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis() < end) { //continuously adding offers for 10 sec
            // resource as key; participants fight to add to the same key etc...
            // list of objects as value

            Offer toAdd=new Offer(this);

            // Get the list according to the ticker and add the offer to that list.

            int indexToAdd = Matchmaker.findIndex(toAdd.getTicker(),toAdd.getPrice(),0);
            Screen.offers.get(toAdd.getTicker()).add(indexToAdd,toAdd);

            if(toAdd.getPrice()>0){
                Sender buySender= new Sender("Buy Channel");
                buySender.sendMessage(toAdd);
            }
            else{
                Sender sellSender= new Sender("Sell Channel");
                sellSender.sendMessage(toAdd);
            }

            System.out.println(participantID + " added:\n" +
                    "┌───┬───────┬──────┬───────────┬─────┬────────┬─────────┐\n" +
                    "│ ID│Tickers│Status│Participant│Price│Quantity│Tolerance│\n" +
                    toAdd +
                    "\n└───┴───────┴──────┴───────────┴─────┴────────┴─────────┘" +
                    "\n");

            try {
                Thread.sleep(rand.nextInt(500, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printBalance();
    }

    public void updateStaleOffer(String offerID){

    }

    public void notifyTransaction(int offerID, int price, int quantity, Offer.saleEnum Status){
        int sum = balance.get() + price * quantity * -1;
        balance.set(sum);
        /*System.out.println(participantID + " just " + (Status == Offer.saleEnum.SELL ? "sold " : "bought ")
                + offerID + ", " + quantity + " units with a price of "
                + (Status == Offer.saleEnum.SELL ? -1 * price : price)
                + ".\nCurrent balance is " + this.balance);*/
    }

    @Override
    public void run() {
        try {
            addOffers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
