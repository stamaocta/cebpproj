import java.util.ArrayList;
import java.util.List;

public class StockExchangeMain {
    public static void main(String[] args) {
        Screen myScreen = new Screen();
        Participant participant1= new Participant("Nico",List.of("*", "**", "***","****","*****"),myScreen);
        Participant participant2= new Participant("Didi",List.of("^", "^^", "^^^","^^^^","^^^^^","^^^^^^"),myScreen);
        Participant participant3= new Participant("Stama",List.of("$", "$$", "$$$","$$$$","$$$$$"),myScreen);
        Participant participant4= new Participant("Marius",List.of("@", "@@", "@@@","@@@@"),myScreen);

        Thread t1=new Thread(participant1);
        Thread t2=new Thread(participant2);
        Thread t3=new Thread(participant3);
        Thread t4=new Thread(participant4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        System.out.println("Final list of offers:");
        for (int i = 0; i < myScreen.offers.size(); i++) {
            System.out.println("Element " + (i + 1) + ": " + myScreen.offers.get(i));
        }
        */

}
}
