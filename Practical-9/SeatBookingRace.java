
class Booking {
    int seatsLeft = 5;
    int booked = 0;

    void book() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            seatsLeft--;
            booked++;
            System.out.println("Seat booked");
        }
    }
}

class SafeBooking {
    int seatsLeft = 5;
    int booked = 0;

    synchronized void book() {
        if (seatsLeft > 0) {
            seatsLeft--;
            booked++;
            System.out.println("Seat booked");
        }
    }
}

public class SeatBookingRace {
    public static void main(String[] args) throws InterruptedException {
        Booking b1 = new Booking();
        SafeBooking b2 = new SafeBooking();

        Thread[] t1 = new Thread[10];
        Thread[] t2 = new Thread[10];

        for (int i = 0; i < 10; i++) {
            t1[i] = new Thread(() -> b1.book());
            t2[i] = new Thread(() -> b2.book());

            t1[i].start();
            t2[i].start();
        }

        for (int i = 0; i < 10; i++) {
            t1[i].join();
            t2[i].join();
        }

        System.out.println("Without synchronization: " + b1.booked);
        System.out.println("With synchronization: " + b2.booked);
    }
}
