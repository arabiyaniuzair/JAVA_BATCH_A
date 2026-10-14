package Project.src.service;




import Project.src.model.Account;
import Project.src.model.SavingsAccount;
import Project.src.util.Command;
import Project.src.util.CommandParser;
import Project.src.util.StatementFormatter;
import static Project.src.util.Validator.isValidMobile;

public class MiniBank {

    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount("SB101", 10000);

        System.out.println("MiniBank Application");
    

        account.deposit(2000);
        account.withdraw(1000);

        System.out.println(StatementFormatter.format(account));
        System.out.println("Yearly Interest: " + account.yearlyInterest());

        if (account instanceof Premium) {
            System.out.println("Premium Account: Yes");
        }

        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && amount <= account.getBalance();
            }
        };

        System.out.println("Anonymous Class: "
                + rule1.allow(account, 500));

        WithdrawRule rule2 = (acc, amount) ->
                amount > 0 && amount <= acc.getBalance();

        System.out.println("Lambda Expression: "
                + rule2.allow(account, 500));

        System.out.println("Valid Mobile: "
                + isValidMobile("9876543210"));

        Command command = CommandParser.parse("deposit 500");

        if (command != null) {
            System.out.println("Command: " + command.getType());
            System.out.println("Amount: " + command.getAmount());
        }
    }
}
