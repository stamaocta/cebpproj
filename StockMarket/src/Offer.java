import java.util.Arrays;
import java.util.List;
import java.util.Random;


// make ID static and iterate upon it whenever creating a new offer to have unique offers?
// same with participant?

public class Offer {
    enum saleEnum {SELL, BUY};

    private static int numInstance = 0;
    private int offerID; // unique ID of the offer
    private String ticker; // selling potatoes, USD, etc...
    private saleEnum saleStatus;
    private Participant participant; // the owner who made the class
    private double price;
    private int quantity;

    public int getOfferID() {
        return offerID;
    }

    public String getTicker() {
        return ticker;
    }

    public saleEnum isForSale() {
        return saleStatus;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Offer(String ticker, saleEnum saleStatus, Participant participant, double price, int quantity) {
        this.offerID = numInstance;
        numInstance += 1;
        this.ticker = ticker;
        this.saleStatus = saleStatus;
        this.participant = participant;
        this.price = price;
        this.quantity = quantity;
    }

    public Offer(Participant participant) {
        this.offerID = numInstance;
        numInstance += 1;

        List<String> randomResourceList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        // pick a random resource from the aforementioned list
        this.ticker = randomResourceList.get(rand.nextInt(randomResourceList.size()));
        if(rand.nextBoolean())
            this.saleStatus=saleEnum.SELL;
        else
            this.saleStatus=saleEnum.BUY;
        this.participant = participant;

        this.price=rand.nextDouble(100,500);
        this.quantity= rand.nextInt(1,100);
//        System.out.println(this);
    }

    @Override
    public String toString() {
        String saleSts;
        if(saleStatus==saleEnum.BUY) saleSts="BUY";
        else saleSts="SELL";

        return "Offer{" +
                "offerID=" + offerID +
                ", ticker='" + ticker + '\'' +
                ", saleStatus=" + saleSts +
                ", participant=" + participant.getParticipantID() +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
