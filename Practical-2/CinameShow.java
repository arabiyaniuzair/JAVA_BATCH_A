import java.util.Scanner;

class Booking {
    private String title;
    private int Seatavailable;
    private final int capacity;
    private static int totalBooked = 0;

    Booking(String title, int capacity) {
        this.title = title;
        this.Seatavailable = capacity;
        this.capacity=capacity;
        totalBooked++;
    }

    Booking(String title) {
        this(title, 100);
    }

    boolean book(int n) {
        if (n <= Seatavailable) {
            Seatavailable -= n;
            totalBooked += n;
            return true;
        } else {
            return false;
        }
    }

    void cancel(int n) {
         Seatavailable += n;
        if (Seatavailable>capacity) {
           Seatavailable=capacity;
        }
    }

    int getSeatsAvailable() {
        return Seatavailable;
    }

    static int getTotalBooked() {
        return totalBooked;
    }
}

public class CinameShow {
    public static void main(String[] args) {
        Booking b1 = new Booking("Kalki2");
        Booking b2 = new Booking("Kalki", 150);
         System.out.println("Book 30 seats: " + b1.book(30));
        System.out.println("Seats Available: " + b1.getSeatsAvailable());

        System.out.println("Book 80 seats: " + b1.book(80));
        System.out.println("Seats Available: " + b1.getSeatsAvailable());

        b1.cancel(20);
        System.out.println("After Cancel 20 Seats: " + b1.getSeatsAvailable());

        System.out.println("Book 50 seats: " + b2.book(50));
        System.out.println("Seats Available: " + b2.getSeatsAvailable());

        System.out.println("Total Booked Seats: " + Booking.getTotalBooked());
    }
}
