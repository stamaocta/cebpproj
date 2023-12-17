package stockExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;


// make ID static and iterate upon it whenever creating a new offer to have unique offers?
// same with participant?

public class Offer {
    enum saleEnum {SELL, BUY};

    private static AtomicInteger numInstance = new AtomicInteger();
    private int offerID; // unique ID of the offer
    private String ticker; // selling potatoes, USD, etc...
    private saleEnum saleStatus;
    private Participant participant; // the owner who made the class
    private int price;
    private int quantity;
    // TODO: CALLBACK? Participants update, based on private int staleness;

    public int getOfferID() {
        return offerID;
    }

    public String getTicker() {
        return ticker;
    }

    public int getPrice() {return price; }

    public int getQuantity() {
        return quantity;
    }

    public saleEnum getSaleStatus() {
        return saleStatus;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public saleEnum isForSale() {
        return saleStatus;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Offer(int offerID, String ticker, saleEnum saleStatus, Participant participant, int price, int quantity) {
        this.offerID = offerID;
        this.ticker = ticker;
        this.saleStatus = saleStatus;
        this.participant = participant;
        this.price = price;
        this.quantity = quantity;
    }


    public Offer(Participant participant) {
        this.offerID = numInstance.incrementAndGet();

        List<String> randomResourceList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        // pick a random resource from the aforementioned list
        this.ticker = randomResourceList.get(rand.nextInt(randomResourceList.size()));

        this.price=rand.nextInt(100,500);
        this.quantity= rand.nextInt(1,100);

        if(rand.nextBoolean()) {
            this.saleStatus = saleEnum.SELL;
            this.price = this.price * -1;
        }
        else {
            this.saleStatus = saleEnum.BUY;
        }
        this.participant = participant;
//        System.out.println(this);
    }

    public boolean matches(Offer toMatch){
        return this.getTicker().equals(toMatch.getTicker()) &&
                this.isForSale() != toMatch.isForSale() &&
                abs(this.price + toMatch.price) < 10;
    }

    @Override
    public String toString() {
        String saleSts;
        if(saleStatus==saleEnum.BUY) saleSts="BUY";
        else saleSts="SELL";

        return "stockExchange.Offer{" +
                "offerID=" + offerID +
                ", ticker='" + ticker + '\'' +
                ", saleStatus=" + saleSts +
                ", participant=" + participant.getParticipantID() +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
