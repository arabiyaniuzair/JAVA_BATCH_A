package Project.src.util;




import  Project.src.model.Account;

public class StatementFormatter {

    public static String format(Account account) {
        return "Account Number: " + account.getAccountNumber()
                + "\nBalance: " + account.getBalance();
    }
}

