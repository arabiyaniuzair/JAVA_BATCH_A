package Project;

import java.util.Scanner;
import java.util.regex.Pattern;

class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;

    private static long customerCounter = 101;

    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
        this.address = new Address("Main Road", "Botad", "364710");
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public static class Address {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getline() {
            return line;
        }

        public String getcity() {
            return city;
        }

        public String getpincode() {
            return pincode;
        }
    }
}

class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    public Account(String ownerName, long balance) {
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
        this.accountNumber = generateAccountNumber();
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "AccountNumber: " + accountNumber + ", Owber: " + ownerName + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

}

class Validator {
    public static final Pattern MOBILE = Pattern.compile("^[6-9][0-9]{9}$");
    public static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9_.]+$");
    public static final Pattern PAN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    public static final Pattern IFSC = Pattern.compile("^[A-Z]{4}[0][A-Z0-9]{6}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email!= null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan!= null && PAN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc!= null && IFSC.matcher(ifsc).matches();
    }
}
    enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    record Command(TransactionType type, String accountNumber, long amount) {
    }

    class CommandParser {
    public static Command parse(String line){
        String parts[]=line.split(" ");
        TransactionType type=TransactionType.valueOf(parts[0]);
        String accountNumber=parts[1];
        long amount=Long.parseLong(parts[2]);
        return new Command(type, accountNumber, amount);
        }
}
    class StatementFormatter{
      public static String buildStatement(Account account){
        StringBuilder sb=new StringBuilder();
        sb.append("Account Statement\n");
        sb.append("Account Number: ")
          .append(account.getAccountNumber())
          .append("\n");

        sb.append("Owner Name: ")
          .append(account.getOwnerName())
          .append("\n");

        sb.append("Balance: ")
          .append(account.getBalance())
          .append("\n");

        sb.append("Active: ")
          .append(account.isActive())
          .append("\n");
        
        return sb.toString();
      }
    }


public class Minibank {
    record BankInfo(String name, String branch) {
    };

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    System.out.println("Validator Testing");
    
    System.out.println("Mobile 9876543210: " + Validator.isValidMobile("9876543210"));

    System.out.println("Mobile 1234567890: " + Validator.isValidMobile("1234567890"));

    System.out.println("Email uzair@gmail.com: " + Validator.isValidEmail("uzair@gmail.com"));

    System.out.println("Email uzairgmail.com: " + Validator.isValidEmail("uzairgmail.com"));

    System.out.println("PAN ABCDE1234F: " + Validator.isValidPan("ABCDE1234F"));

    System.out.println("PAN ABC1234F: " + Validator.isValidPan("ABC1234F"));

    System.out.println("IFSC SBIN0001234: " + Validator.isValidIfsc("SBIN0001234"));

    System.out.println("IFSC SBI1234: " + Validator.isValidIfsc("SBI1234"));

    System.out.println("\nCommand Parsing");

    Command command = CommandParser.parse("DEPOSIT AC0001 500");
    System.out.println("Type: " + command.type());
    System.out.println("Account Number: " + command.accountNumber());
    System.out.println("Amount: " + command.amount());
    
    Account testAccount = new Account("Uzair", 5000);
    System.out.println("\nAccount Statement");
    System.out.println(StatementFormatter.buildStatement(testAccount));
    int choice;
    BankInfo bank = new BankInfo("SBI", "Botadbank");
        do {

            System.out.println("1.OPEN_ACCOUNT");
            System.out.println("2.DEPOSIT");
            System.out.println("3.WITHDRAW");
            System.out.println("4.TRANSFER");
            System.out.println("5.EXIT");

            System.out.println("Enter Your Choice: ");
            choice = scanner.nextInt();
            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };
            if (option == null) {
                System.out.println("Invalid choice.Please enter a choice between 1 and 5");
            }

            switch (option) {
                case OPEN_ACCOUNT -> {

                    Customer c1 = new Customer("Uzair", "uzair@gmail.com", "9876543210");
                    Customer c2 = new Customer("Ali", "ali@gmail.com", "9999999999");
                    Customer c3 = new Customer("Ahmed", "ahmed@gmail.com", "8888888888");

                    Account[] accounts = new Account[3];
                    accounts[0] = new Account(c1.getName(), 1000);
                    accounts[1] = new Account(c2.getName());
                    accounts[2] = new Account(c3.getName(), 500);
                    for (Account acc : accounts) {
                        System.out.println(acc);
                    }
                    System.out.println("Account-1 equals Account-2: " + accounts[0].equals(accounts[1]));
                    Object obj = accounts[0];

                    if (obj instanceof Account) {
                        System.out.println("obj is an Account object.");
                    }
                    Customer copy = c1.clone();
                    System.out.println("Original Customer: " + c1.getName());
                    System.out.println("Copy of Customer: " + copy.getName());

                    System.out.println("Line" + c1.getAddress().getline());
                    System.out.println("City: " + c1.getAddress().getcity());
                    System.out.println("PinCode: " + c1.getAddress().getpincode());

                    accounts[0].deposit(500);
                    accounts[0].withdraw(200);

                    accounts[1].deposit(1000);
                    accounts[1].withdraw(300);

                    accounts[2].withdraw(600);
                    accounts[2].deposit(200);

                    System.out.println("\nAccount Details");

                    for (int i = 0; i < accounts.length; i++) {

                        System.out.println("Customer ID   : " +
                                (i == 0 ? c1.getCustomerId()
                                        : i == 1 ? c2.getCustomerId()
                                                : c3.getCustomerId()));

                        System.out.println("Owner: " + accounts[i].getOwnerName());
                        System.out.println("Account No.: " + accounts[i].getAccountNumber());
                        System.out.println("Balance: " + accounts[i].getBalance());
                        System.out.println("Active: " + accounts[i].isActive());
                        System.out.println();
                    }
                }
                case DEPOSIT ->
                    System.out.println("Deposit - to be implemented in a later lab");
                case WITHDRAW ->
                    System.out.println("Withdraw - to be implemented in a later lab");
                case TRANSFER ->
                    System.out.println("Transfer - to be implemented in a later lab");
                case EXIT ->
                    System.out.println("Thank you for using MiniBank. Goodbye!");
            }
        } while (choice != 5);
        scanner.close();
    }
}
