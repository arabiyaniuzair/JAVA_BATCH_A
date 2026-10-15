package Project;

import java.util.Scanner;
import java.util.regex.Pattern;
import  Project.src.model.annotation.Id;
import Project.src.model.annotation.Positive;
import Project.src.model.annotation.MaxLength;
import Project.src.util.AnnotationValidator;
import Project.src.exception.BankException;
import Project.src.exception.InsufficientFundsException;
import Project.src.exception.InvalidAmountException;
import Project.src.exception.AccountNotFoundException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.StandardCharsets;

class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
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

    static class Address implements Serializable {
    private static final long serialVersionUID = 1L;
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

abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;

@Id
@MaxLength(20)
private final String accountNumber;

private String ownerName;

@Positive
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

   public synchronized void deposit(long amount) throws InvalidAmountException {
    if (amount <= 0) {
        throw new InvalidAmountException("Amount must be positive.");
    }
    balance += amount;
   }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public synchronized boolean withdraw(long amount)
        throws InsufficientFundsException, InvalidAmountException {
     if (amount <= 0) {
        throw new InvalidAmountException("Amount must be positive.");
     }
     if (!canWithdraw(amount)) {
        throw new InsufficientFundsException(amount - balance);
     }
     balance -= amount;
     return true;
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
    public void transfer(Account to, long amount) throws BankException {
    try {
        if (to == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        withdraw(amount);
        to.deposit(amount);

        System.out.println("Transfer successful.");

    } catch (BankException e) {
        throw e;

    } finally {
        System.out.println("Transfer operation completed.");
    }
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

class SavingsAccount extends Account{
    private static final long serialVersionUID = 1L;
   private long minBalance;

   public SavingsAccount(String ownerName,long balance,long minBalance){
        super(ownerName, balance);
        this.minBalance=minBalance;
    }
    @Override 
    public double interestRate(){
        return 4.0;
    }
    @Override 
    public  boolean canWithdraw(long amount){
        return  getBalance()-amount>=minBalance;
    }

}
 
class CurrentAccount extends Account{
    private static final long serialVersionUID = 1L;
     public long overdraftLimit;
     public CurrentAccount(String ownerName, long balance, long overdraftLimit){
        super(ownerName, balance);
        this.overdraftLimit=overdraftLimit;
    }

    @Override 
    public double interestRate(){
        return  0;
    }
    @Override 
    public boolean canWithdraw(long amount){
        return getBalance()-amount>=-overdraftLimit;
    }
}
class FixedDepositAccount extends Account{
    private static final long serialVersionUID = 1L;
    public FixedDepositAccount(String ownerName,long balance){
        super(ownerName, balance);
    }

    @Override
    public double interestRate(){
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount){
        return false;
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
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC.matcher(ifsc).matches();
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
    public static Command parse(String line) {
        String parts[] = line.split(" ");
        TransactionType type = TransactionType.valueOf(parts[0]);
        String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(type, accountNumber, amount);
    }
}

class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
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
class BankResource implements AutoCloseable {
    public void close() {
        System.out.println("Resource closed.");
    }
}
class AccountWorker implements Runnable {
    private Account account;
    private int times;
    private long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        for (int i = 0; i < times; i++) {
            try {
                account.deposit(amount);
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(Thread.currentThread().getName() + " finished");
    }
}

class TransactionProcessor {
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void stop() {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

class TransactionBuffer {
    private Runnable[] buffer = new Runnable[5];
    private int count = 0;

    public synchronized void put(Runnable task) throws InterruptedException {
        while (count == buffer.length) {
            wait();
        }

        buffer[count++] = task;
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (count == 0) {
            wait();
        }

        Runnable task = buffer[0];

        for (int i = 1; i < count; i++) {
            buffer[i - 1] = buffer[i];
        }

        buffer[--count] = null;
        notifyAll();

        return task;
    }
}

class TransactionProducer implements Runnable {
    private TransactionBuffer buffer;

    public TransactionProducer(TransactionBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                final int transaction = i;

                buffer.put(() ->
                    System.out.println("Processing transaction " + transaction)
                );
            }

            buffer.put(null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class TransactionConsumer implements Runnable {
    private TransactionBuffer buffer;

    public TransactionConsumer(TransactionBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {
                Runnable task = buffer.take();

                if (task == null) {
                    break;
                }

                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class SafeTransfer {
    public static void transfer(Account a, Account b, long amount)
            throws BankException {

        Account first = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0
                ? a : b;

        Account second = first == a ? b : a;

        synchronized (first) {
            synchronized (second) {
                a.withdraw(amount);
                b.deposit(amount);
                System.out.println("Transfer completed.");
            }
        }
    }
}

class StatePersister {
    public static void save(Account[] accounts, Path file) {
        try {
            Path parent = file.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (ObjectOutputStream out = new ObjectOutputStream(
                    Files.newOutputStream(file))) {
                out.writeObject(accounts);
            }

            System.out.println("Accounts saved successfully.");

        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public static Account[] load(Path file) {
        try (ObjectInputStream in = new ObjectInputStream(
                Files.newInputStream(file))) {
            return (Account[]) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load error: " + e.getMessage());
            return new Account[0];
        }
    }
}

class TransactionLog {
    public static void append(Path file, String line) {
        try {
            Path parent = file.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.write(
                file,
                (line + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        }
    }
}

class ReportGenerator {
    public static void generate(Path logsFolder, Path reportFile) {
        long totalDeposits = 0;
        long totalWithdrawals = 0;

        StringBuilder report = new StringBuilder();

        try (DirectoryStream<Path> files =
                Files.newDirectoryStream(logsFolder)) {

            for (Path file : files) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }

                BasicFileAttributes attributes =
                    Files.readAttributes(file, BasicFileAttributes.class);

                report.append("File: ")
                      .append(file.getFileName()).append("\n");

                report.append("Size: ")
                      .append(attributes.size()).append(" bytes\n");

                report.append("Last modified: ")
                      .append(attributes.lastModifiedTime()).append("\n");

                try (BufferedReader reader =
                        Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length == 2) {
                            try {
                                long amount = Long.parseLong(parts[1]);

                                if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                    totalDeposits += amount;
                                } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                    totalWithdrawals += amount;
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid transaction: " + line);
                            }
                        }
                    }
                }

                report.append("\n");
            }

            report.append("Total deposits: ")
                  .append(totalDeposits).append("\n");

            report.append("Total withdrawals: ")
                  .append(totalWithdrawals).append("\n");

            Path parent = reportFile.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.writeString(
                reportFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );

            System.out.println(report);
            System.out.println("Report saved to: " + reportFile.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Report error: " + e.getMessage());
        }
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
        System.out.println("\nMultithreading Test");

         Account account = new SavingsAccount("Uzair", 10000, 0);

         Thread[] threads = new Thread[10];

         for (int i = 0; i < threads.length; i++) {         
    threads[i] = new Thread(new AccountWorker(account, 1000, 10),
            "Worker-" + (i + 1));
  
    threads[i].start();
}

         for (Thread thread : threads) {
             try {
                          thread.join();
             } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                 System.out.println("Thread interrupted.");
                 return;
             }
         }

        System.out.println("Final balance: " + account.getBalance());
        System.out.println("Validator Testing");
        System.out.println("Mobile 9876543210: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile 1234567890: " + Validator.isValidMobile("1234567890"));
        System.out.println("Email uzair@gmail.com: " + Validator.isValidEmail("uzair@gmail.com"));
        System.out.println("Email uzairgmail.com: " + Validator.isValidEmail("uzairgmail.com"));
        System.out.println("PAN ABCDE1234F: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN ABC1234F: " + Validator.isValidPan("ABC1234F"));
        System.out.println("IFSC SBIN0001234: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC SBI1234: " + Validator.isValidIfsc("SBI1234"));
        System.out.println("\nTransaction Processor Test");

    Account account1 = new SavingsAccount("Uzair", 10000, 0);
    Account account2 = new SavingsAccount("Ali", 5000, 0);

    TransactionProcessor processor = new TransactionProcessor();

    for (int i = 1; i <= 10; i++) {
        processor.submit(() -> {
            try {
                account1.deposit(100);
                System.out.println(
                    Thread.currentThread().getName() + " deposited 100"
                );
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        });
    }

       processor.stop();

        System.out.println("Final transaction balance: " + account1.getBalance());
         System.out.println("\nProducer Consumer Test");
        TransactionBuffer buffer = new TransactionBuffer();

        Thread consumer = new Thread(
            new TransactionConsumer(buffer), "Consumer"
        );
        Thread producer = new Thread(
            new TransactionProducer(buffer), "Producer"
         );

         consumer.start();
         producer.start();

       try {
          producer.join();
           consumer.join();
       } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
            scanner.close();
           return;
            }

            System.out.println("\nSafe Transfer Test");

             try {
              SafeTransfer.transfer(account1, account2, 500);
           } catch (BankException e) {
                   System.out.println(e.getMessage());
         }

        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());
        System.out.println("\nPersistence Testing");

         Account[] savedAccounts = {
            new SavingsAccount("Uzair", 10000, 0),
            new CurrentAccount("Ali", 5000, 2000),
            new FixedDepositAccount("Ahmed", 8000)
        };

        Path dataFolder = Path.of("minibank-data");
        Path accountFile = dataFolder.resolve("accounts.dat");
        Path logsFolder = dataFolder.resolve("logs");
        Path logFile = logsFolder.resolve("transactions.txt");
        Path reportFile = dataFolder.resolve("daily-report.txt");

        StatePersister.save(savedAccounts, accountFile);

        Account[] loadedAccounts = StatePersister.load(accountFile);

         System.out.println("\nLoaded Accounts");

             for (Account acc : loadedAccounts) {
                System.out.println(acc);
              }
          
           System.out.println("\nTransaction Logging");

          TransactionLog.append(logFile, "DEPOSIT 1000");
          TransactionLog.append(logFile, "WITHDRAW 200");
          TransactionLog.append(logFile, "DEPOSIT 500");
          TransactionLog.append(logFile, "WITHDRAW 100");

          System.out.println("\nEnd-of-Day Report");
        ReportGenerator.generate(logsFolder, reportFile);
        System.out.println("\nCommand Parsing");

        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        Account testAccount=new SavingsAccount("Uzair", 5000, 1000);
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
                    Account invalidAccount = new SavingsAccount("Test", -500, 0);

                    String[] errors = AnnotationValidator.validate(invalidAccount);

                     for (String error : errors) {
                           System.out.println(error);
                       }
                    Customer c2 = new Customer("Ali", "ali@gmail.com", "9999999999");
                    Customer c3 = new Customer("Ahmed", "ahmed@gmail.com", "8888888888");

                    Account[] accounts = new Account[3];
                    accounts[0] = new SavingsAccount(c1.getName(), 1000, 500);
                    accounts[1] = new CurrentAccount(c2.getName(), 500, 2000);
                    accounts[2] = new FixedDepositAccount(c3.getName(), 5000);

                    for (Account acc : accounts) {
                       System.out.println(
                       acc.getOwnerName()+ " : " +acc.interestRate()+"%"
                       );
                       }
                    for (Account acc : accounts) {
                        System.out.println(acc);
                    }
                    System.out.println("Account-1 equals Account-2: " + accounts[0].equals(accounts[1]));
                    if(accounts[0] instanceof SavingsAccount savings){
                         System.out.println(
                                "Savings account found. Interest rate: "
                                   + savings.interestRate() + "%"
                                  );
                                }
                    Customer copy = c1.clone();
                    System.out.println("Original Customer: " + c1.getName());
                    System.out.println("Copy of Customer: " + copy.getName());

                    System.out.println("Line" + c1.getAddress().getline());
                    System.out.println("City: " + c1.getAddress().getcity());
                    System.out.println("PinCode: " + c1.getAddress().getpincode());

                    try {
                        System.out.println("Savings withdraw 200: " + accounts[0].withdraw(200));
                        System.out.println("Current withdraw 1000: " + accounts[1].withdraw(1000));
                        System.out.println("Fixed Deposit withdraw 600: " + accounts[2].withdraw(600));
                      } catch (InvalidAmountException e) {
                        System.out.println("Invalid amount: " + e.getMessage());
                      } catch (InsufficientFundsException e) {
                        System.out.println("Withdrawal failed: " + e.getMessage());
                      } finally {
                        System.out.println("Withdrawal testing completed.");
                      }
                    System.out.println("\nAccount Details");

                    for (int i = 0; i<accounts.length; i++) {

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

            try (BankResource resource = new BankResource()) {
                System.out.println("Bank resource opened.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            scanner.close();
    }
}
