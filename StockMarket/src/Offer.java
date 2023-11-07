import java.util.Arrays;
import java.util.List;
import java.util.Random;


// make ID static and iterate upon it whenever creating a new offer to have unique offers?
// same with participant?

public class Offer {

    private static int numInstance = 0;
    private int ID; // unique ID of the offer
    private String resource; // selling potatoes, USD, etc...
    private boolean forSale; // 0 for buying, 1 for selling
    private String ownerID; // ID string of the owner who made the class

    public int getID() {
        return ID;
    }

    public String getResource() {
        return resource;
    }

    public boolean isForSale() {
        return forSale;
    }

    public Offer(String resource, boolean forSale, String ownerID) {
        this.ID = numInstance;
        numInstance += 1;
        this.resource = resource;
        this.forSale = forSale;
        this.ownerID = ownerID;
    }

    public Offer(String ownerID) {
        this.ID = numInstance;
        numInstance += 1;

        List<String> randomResourceList = Arrays.asList("aaa", "bbb", "ccc", "ddd");

        Random rand = new Random();
        // pick a random resource from the aforementioned list
        this.resource = randomResourceList.get(rand.nextInt(randomResourceList.size()));

        this.forSale = rand.nextInt() % 2 != 0; // pick false or true randomly

        this.ownerID = ownerID;
//        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "ID=" + ID +
                ", resource='" + resource + '\'' +
                ", forSale=" + forSale +
                ", ownerID='" + ownerID + '\'' +
                '}';
    }
}
