
class Counter {
    int count = 0;

    void increment() {
        count++;
    }

    synchronized void safeIncrement() {
        count++;
    }
}

public class CounterRace {
    public static void main(String[] args) throws InterruptedException {
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread[] t1 = new Thread[5];
        Thread[] t2 = new Thread[5];

        for (int i = 0; i < 5; i++) {
            t1[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    c1.increment();
                }
            });

            t2[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    c2.safeIncrement();
                }
            });

            t1[i].start();
            t2[i].start();
        }

        for (int i = 0; i < 5; i++) {
            t1[i].join();
            t2[i].join();
        }

        System.out.println("Without synchronization: " + c1.count);
        System.out.println("With synchronization: " + c2.count);
        System.out.println("Expected count: 500000");
    }
}
