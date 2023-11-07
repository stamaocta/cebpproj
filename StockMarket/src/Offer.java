import java.util.Arrays;
import java.util.List;
import java.util.Random;


// make ID static and iterate upon it whenever creating a new offer to have unique offers?
// same with participant?

public class Offer {

    private static int numInstance = 0;
    private int offerID; // unique ID of the offer
    private String resource; // selling potatoes, USD, etc...
    private boolean forSale; // 0 for buying, 1 for selling
    private Participant participant; // the owner who made the class

    public int getOfferID() {
        return offerID;
    }

    public String getResource() {
        return resource;
    }

    public boolean isForSale() {
        return forSale;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Offer(String resource, boolean forSale, Participant participant) {
        this.offerID = numInstance;
        numInstance += 1;
        this.resource = resource;
        this.forSale = forSale;
        this.participant = participant;
    }

    public Offer(Participant participant) {
        this.offerID = numInstance;
        numInstance += 1;

        List<String> randomResourceList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        // pick a random resource from the aforementioned list
        this.resource = randomResourceList.get(rand.nextInt(randomResourceList.size()));

        this.forSale = rand.nextInt() % 2 != 0; // pick false or true randomly

        this.participant = participant;
//        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "ID=" + offerID +
                ", resource='" + resource + '\'' +
                ", forSale=" + forSale +
                ", ownerID='" + participant.getParticipantID() + '\'' +
                '}';
    }
}
