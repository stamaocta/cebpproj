import java.util.Arrays;
import java.util.List;
import java.util.Random;


// make ID static and iterate upon it whenever creating a new offer to have unique offers?
// same with participant?

public class Offer {
    private String ID;
    private boolean forSale;
    private String ownerID;

    public Offer(String ID, boolean forSale, String ownerID) {
        this.ID = ID;
        this.forSale = forSale;
        this.ownerID = ownerID;
    }

    public Offer(String ownerID) {
        List<String> randomIDList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        this.ID = randomIDList.get(rand.nextInt(randomIDList.size()));

        if(rand.nextInt() % 2 == 0){
            this.forSale = false;
        } else { this.forSale = true; }

        this.ownerID = ownerID;
//        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "ID='" + ID + '\'' +
                ", forSale=" + forSale +
                ", ownerID='" + ownerID + '\'' +
                '}';
    }
}
