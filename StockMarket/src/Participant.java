import java.util.ArrayList;

public class Participant implements Runnable {
    private String participantID;
    private ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
    public void setPendingOffers(ArrayList<Offer> pendingOffers) {
        this.pendingOffers = pendingOffers;
    }



    public Participant(String participantID) {
        this.participantID = participantID;
    }


    public String getParticipantID() {
        return participantID;
    }



    public void addOfferToPending(Offer toAdd){
        this.pendingOffers.add(toAdd);
    }
    public void populateOffers(int numOffers){
        for (int i = 0; i < numOffers; i++){
            Offer aux = new Offer(this);
            addOfferToPending(aux);
        }
    }

    public void addOffers(){
        for (Offer offer : pendingOffers) {
            Screen.offers.put(offer.getOfferID(), offer);
            //System.out.println(participantID + " added " + offer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // delete offers after pushing?
        }
    }

    public void notifyTransaction(int offerID){
        System.out.println(participantID + " just transactioned " + offerID);
    }

    @Override
    public void run() {
        addOffers();
    }


}
