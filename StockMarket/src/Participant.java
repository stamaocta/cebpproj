import java.sql.ClientInfoStatus;
import java.util.ArrayList;

public class Participant implements Runnable {
    private String participantID;
    public Participant(String participantID) {
        this.participantID = participantID;
    }
    public String getParticipantID() {
        return participantID;
    }
    public void addOffers(){
        long end=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis() < end) { //continuously adding offers for 10 sec
            // resource as key; participants fight to add to the same key etc...
            // list of objects as value

//            if(Screen.offers(resource) exists as list)
//                Add offer to that list
//            else
//                create list corresponding to that resource

            Offer toAdd=new Offer(this);
            Screen.offers.put(toAdd.getOfferID(),toAdd);
            //System.out.println(participantID + " added " + offer);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
