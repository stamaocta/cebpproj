package stockExchange;

import messageService.Sender;
import stockExchange.Offer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Participant implements Runnable {
    private String participantID;


    public Participant(String participantID) {
        this.participantID = participantID;
    }


    public String getParticipantID() {
        return participantID;
    }


    public void addOffers() throws IOException, TimeoutException {
        long end=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis() < end) { //continuously adding offers for 10 sec
            // resource as key; participants fight to add to the same key etc...
            // list of objects as value

            Offer toAdd=new Offer(this);

            // Get the list according to the ticker and add the offer to that list.

            int indexToAdd = Screen.findIndex(toAdd.getTicker(),toAdd.getPrice());
            Screen.offers.get(toAdd.getTicker()).add(indexToAdd,toAdd);

            if(toAdd.getPrice()>0){
                Sender buySender= new Sender("Buy Channel");
                buySender.sendMessage("Offer "+ toAdd.toString() + " was added");
            }
            else{
                Sender sellSender= new Sender("Sell Channel");
                sellSender.sendMessage("Offer "+ toAdd.toString() + " was added");
            }

            //System.out.println(participantID + " added " + offer);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStaleOffer(String offerID){

    }

    public void notifyTransaction(int offerID){
        System.out.println(participantID + " just transactioned " + offerID);
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
