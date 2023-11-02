import java.util.List;

public class Participant implements Runnable {
    private String participantID;
    private List<String> pendingOffers;
    private Screen myScreen;
    public Participant(String participantID, List<String> pendingOffers,Screen screen) {
        this.participantID = participantID;
        this.pendingOffers=pendingOffers;
        this.myScreen=screen;
    }

    public void addOffers(){
        for (String offer : pendingOffers) {
            myScreen.offers.add(offer);
            System.out.println(participantID + " added " + offer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        addOffers();
    }
}
