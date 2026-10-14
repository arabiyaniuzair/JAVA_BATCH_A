package Project.src.model;



import Project.src.service.Transactable;
import Project.src.service.InterestBearing;

public class Account implements Transactable, InterestBearing {
    private String accountNumber;
    private long balance;

    public Account(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful");
        } else {
            System.out.println("Amount must be positive");
        }
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful");
            return true;
        }
        System.out.println("Invalid amount or insufficient balance");
        return false;
    }

    @Override
    public double interestRate() {
        return 2.0;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}
