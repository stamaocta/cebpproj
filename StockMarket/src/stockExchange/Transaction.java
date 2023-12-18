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
        return "Selling Offer & Buying Offer:\n" +
                "┌───┬───────┬──────┬───────────┬─────┬────────┬─────────┐\n" +
                "│ ID│Tickers│Status│Participant│Price│Quantity│Tolerance│\n" +
                (offer1.getSaleStatus() == Offer.saleEnum.SELL ? offer1 : offer2) + '\n' +
                (offer1.getSaleStatus() == Offer.saleEnum.BUY ? offer1 : offer2) +
                "\n└───┴───────┴──────┴───────────┴─────┴────────┴─────────┘\n";
    }
}
