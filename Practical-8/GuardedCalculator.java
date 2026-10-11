
import java.util.Scanner;

class DivideByZeroException extends Exception {
    DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        while (!success) {
            try {
                System.out.print("Enter first number: ");
                double a = Double.parseDouble(sc.next());

                System.out.print("Enter operator (+, -, *, /): ");
                String op = sc.next();

                System.out.print("Enter second number: ");
                double b = Double.parseDouble(sc.next());

                double result = 0;

                switch (op) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        if (b == 0) {
                            throw new DivideByZeroException(
                                    "Cannot divide by zero");
                        }
                        result = a / b;
                        break;
                    default:
                        System.out.println("Invalid operator");
                        continue;
                }

                System.out.println("Result: " + result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter valid numbers.");
            } catch (DivideByZeroException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Calculation attempt completed.");
            }
        }

        sc.close();
    }
}
