package Project.src.util;



import Project.src.model.TransactionType;

public class CommandParser {

    public static Command parse(String input) {
        if (input == null) {
            return null;
        }

        String[] parts = input.trim().split("\\s+");

        if (parts.length != 2) {
            return null;
        }

        try {
            long amount = Long.parseLong(parts[1]);

            if (parts[0].equalsIgnoreCase("deposit")) {
                return new Command(TransactionType.DEPOSIT, amount);
            }

            if (parts[0].equalsIgnoreCase("withdraw")) {
                return new Command(TransactionType.WITHDRAW, amount);
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return null;
    }
}

