package stockExchange;

import java.util.concurrent.CopyOnWriteArrayList;

public class StockExchangeMain {
    public static void main(String[] args) {

        Screen screen = new Screen();

        Matchmaker matchmaker1 = new Matchmaker("aaa");
        Matchmaker matchmaker2 = new Matchmaker("bbb");
        Matchmaker matchmaker3 = new Matchmaker("ccc");
        Matchmaker matchmaker4 = new Matchmaker("ddd");
        Participant participant1= new Participant("Nico");
        Participant participant2= new Participant("Didi");
        Participant participant3= new Participant("Stama");
        Participant participant4= new Participant("Marius");

        Thread m1 = new Thread(matchmaker1);
        Thread m2 = new Thread(matchmaker2);
        Thread m3 = new Thread(matchmaker3);
        Thread m4 = new Thread(matchmaker4);
        Thread t1=new Thread(participant1);
        Thread t2=new Thread(participant2);
        Thread t3=new Thread(participant3);
        Thread t4=new Thread(participant4);

        m1.setName("Thread-aaa");
        m2.setName("Thread-bbb");
        m3.setName("Thread-ccc");
        m4.setName("Thread-ddd");
        t1.setName("Thread-Nico");
        t2.setName("Thread-Didi");
        t3.setName("Thread-Stama");
        t4.setName("Thread-Marius");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        m1.start();
        m2.start();
        m3.start();
        m4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            /*m1.join();
            m2.join();
            m3.join();
            m4.join();*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            Thread.sleep(10000);
        } catch(Exception e){}
        System.out.println("Final list of offers:\n");
        for (String i: Screen.offers.keySet()) {

            CopyOnWriteArrayList<Offer> currentRow = Screen.offers.get(i);

            System.out.println("┌───┬───────┬──────┬───────────┬─────┬────────┬─────────┐");
            System.out.println("│ ID│Tickers│Status│Participant│Price│Quantity│Tolerance│");
            for (Offer off: currentRow) System.out.println(off);
            System.out.println("└───┴───────┴──────┴───────────┴─────┴────────┴─────────┘");
        }


}
}
