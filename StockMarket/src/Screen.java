import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Screen class
public class Screen {
    public List<String> offers;

    public Screen() {
        this.offers = new CopyOnWriteArrayList<String>();
    }


}
