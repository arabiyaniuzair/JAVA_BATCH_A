package Project.src.model;




public class FixedDepositAccount extends Account {

    public FixedDepositAccount(String accountNumber, long balance) {
        super(accountNumber, balance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }
}

