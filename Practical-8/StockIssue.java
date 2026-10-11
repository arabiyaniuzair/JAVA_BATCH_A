
import java.util.*;

class OutOfStockException extends Exception {
    int shortfall;

    OutOfStockException(int shortfall) {
        super("Out of stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    int stock = 20;

    void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero");
        }

        if (qty > stock) {
            throw new OutOfStockException(qty - stock);
        }

        stock -= qty;
        System.out.println(item + " issued successfully: " + qty);
        System.out.println("Remaining stock: " + stock);
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();

        System.out.print("Enter number of requests: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println("Request " + i);

            System.out.print("Enter item name: ");
            String item = sc.next();

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            try {
                warehouse.issue(item, qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
        }

        System.out.println("All requests processed.");
        sc.close();
    }
}
