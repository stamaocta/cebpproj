import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Screen class
// replace list with ConcurrentHashMap which has a key and a value:
// key will be the unique ID of the offer and value will be the actual Offer object with all the data.

public final class Screen {
    public static List<Offer> offers = new CopyOnWriteArrayList<Offer>();

    private Screen() {
    }
}
