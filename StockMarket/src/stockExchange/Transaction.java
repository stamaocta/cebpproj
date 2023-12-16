package stockExchange;

public class Transaction {
    private Offer offer1;
    private Offer offer2;

    public Transaction(Offer offer1, Offer offer2) {
        this.offer1 = offer1;
        this.offer2 = offer2;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "offer1=" + offer1 +
                ", offer2=" + offer2 +
                '}';
    }
}
