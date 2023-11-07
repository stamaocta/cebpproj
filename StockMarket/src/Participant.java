import java.util.ArrayList;
import java.util.List;

public class Participant implements Runnable {
    private String participantID;
    private ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
    public void setPendingOffers(ArrayList<Offer> pendingOffers) {
        this.pendingOffers = pendingOffers;
    }



    public Participant(String participantID) {
        this.participantID = participantID;
    }

    public void addOfferToPending(Offer toAdd){
        this.pendingOffers.add(toAdd);
    }
    public void populateOffers(int numOffers){
        for (int i = 0; i < numOffers; i++){
            Offer aux = new Offer(participantID);
            addOfferToPending(aux);
        }
    }

    public void addOffers(){
        for (Offer offer : pendingOffers) {
            Screen.offers.put(offer.getID(), offer);
            //System.out.println(participantID + " added " + offer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // delete offers after pushing?
        }
    }

    @Override
    public void run() {
        addOffers();
    }
}
