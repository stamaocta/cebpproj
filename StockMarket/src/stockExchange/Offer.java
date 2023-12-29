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
    private int tolerance;
    private int staleness;
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

    public int getTolerance() {return tolerance;}
    public void setTolerance(int t) {
        if (saleStatus == saleEnum.SELL) tolerance += 10;
        else tolerance -= 10;
    }
    public int getStaleness() {return staleness;}
    public void setStaleness() {this.staleness = 1;}

    public Offer(int offerID, String ticker, saleEnum saleStatus, Participant participant, int price, int quantity) {
        this.offerID = offerID;
        this.ticker = ticker;
        this.saleStatus = saleStatus;
        this.participant = participant;
        this.price = price;
        this.quantity = quantity;
        if (saleStatus == saleEnum.SELL) this.tolerance = 10;
        else this.tolerance = -10;
        this.staleness = 1;
    }


    public Offer(Participant participant) {
        this.offerID = numInstance.incrementAndGet();
        this.staleness = 1;

        List<String> randomResourceList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        // pick a random resource from the aforementioned list
        this.ticker = randomResourceList.get(rand.nextInt(randomResourceList.size()));

        this.price=rand.nextInt(100,500);
        this.quantity= rand.nextInt(1,100);

        if(rand.nextBoolean()) {
            this.saleStatus = saleEnum.SELL;
            this.price = this.price * -1;
            this.tolerance = 10;
        }
        else {
            this.saleStatus = saleEnum.BUY;
            this.tolerance = -10;
        }
        this.participant = participant;
//        System.out.println(this);
    }

    public boolean matches(Offer toMatch){
        return this.getTicker().equals(toMatch.getTicker()) &&
                this.isForSale() != toMatch.isForSale() &&
                this.participant.getParticipantID() != toMatch.participant.getParticipantID() &&
                abs(this.price + toMatch.price) <= abs(this.tolerance) &&
                abs(this.price + toMatch.price) <= abs(toMatch.getTolerance());
    }

    @Override
    public String toString() {
        String saleSts;
        if(saleStatus==saleEnum.BUY) saleSts="BUY";
        else saleSts="SELL";

        String finalString;
        //"┌──┬───────┬──────┬───────────┬─────┬────────┐"
        //"│ID│Tickers│Status│Participant│Price│Quantity│"
        //"├──┼───────┼──────┼───────────┼─────┼────────┤"
        //"└──┴───────┴──────┴───────────┴─────┴────────┘"
        finalString = String.format(
                "├───┼───────┼──────┼───────────┼─────┼────────┼─────────┤\n│%3d│%7s│%6s│%11s│%5d│%-8d│%9d│",
                offerID,ticker,saleSts,participant.getParticipantID(),price,quantity,tolerance);

        return finalString;
    }
}
