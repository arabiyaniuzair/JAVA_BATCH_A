package Project.src.model;




import Project.src.service.Premium;

public class SavingsAccount extends Account implements Premium {

    public SavingsAccount(String accountNumber, long balance) {
        super(accountNumber, balance);
    }

    @Override
    public double interestRate() {
        return 4.0;
    }
}
