import java.util.Scanner;

class Temprature {
    private String location;
    private int temprature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    Temprature(String location, int statrtTemp) {
        this.location=location;
        if (statrtTemp>= MIN && statrtTemp<= MAX) {
            temprature=statrtTemp ;
        } else {
            temprature= 22;
        }
        activeCount++;
    }

    Temprature(String l){
        this(l,22);
    }

    void raise() {
        if (temprature< MAX) {
            temprature++;
        } else {
            System.out.println("Already at maximum(30)");
        }
    }

    void lower() {
        if (temprature> MIN) {
            temprature--;
        } else {
            System.out.println("Already at minimun(16)");
        }
    }

    int getTemprature() {
        return temprature;
    }

    static int getActiveCount() {
        return activeCount;
    }
}

public class Thermostat {
    public static void main(String [] args) {
        Temprature t1 = new Temprature("Hotel");
        Temprature t2 = new Temprature("Office", 25);
        for (int i = 0; i < 10; i++) {
            t1.raise();
            System.out.println(t1.getTemprature());
        }
        for (int i = 0; i < 20; i++) {
            t1.lower();
            System.out.println(t1.getTemprature());
        }
         System.out.println("Active Count = " + Temprature.getActiveCount());
    }
}
