package Project.src.model;



public class CurrentAccount extends Account {

    public CurrentAccount(String accountNumber, long balance) {
        super(accountNumber, balance);
    }

    @Override
    public double interestRate() {
        return 1.0;
    }
}
