import java.util.ArrayList;
import java.util.List;

public class StockExchangeMain {
    public static void main(String[] args) {
        Screen screen= new Screen();
        Participant participant1= new Participant("Nico");
        Participant participant2= new Participant("Didi");
        Participant participant3= new Participant("Stama");
        Participant participant4= new Participant("Marius");



        Thread t= new Thread(screen);
        Thread t1=new Thread(participant1);
        Thread t2=new Thread(participant2);
        Thread t3=new Thread(participant3);
        Thread t4=new Thread(participant4);

        t.start();
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

        System.out.println("Final list of offers:");
        for (Integer i: Screen.offers.keySet()) {

            System.out.println( Screen.offers.get(i));
        }


}
}
