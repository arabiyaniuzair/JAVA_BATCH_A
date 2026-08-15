package Project;

import java.util.Scanner;

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

    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int choice;
        BankInfo bank=new BankInfo("SBI", "Botadbank");
        do{

        System.out.println("1.OPEN_ACCOUNT");
        System.out.println("2.DEPOSIT");
        System.out.println("3.WITHDRAW");
        System.out.println("4.TRANSFER");
        System.out.println("5.EXIT");

        System.out.println("Enter Your Choice: ");
        choice=scanner.nextInt();
        MenuOption option=switch(choice){
            case 1->MenuOption.OPEN_ACCOUNT;
            case 2->MenuOption.DEPOSIT;
            case 3->MenuOption.WITHDRAW;
            case 4->MenuOption.TRANSFER;
            case 5->MenuOption.EXIT;
            default->null;
        };
       if(option==null){
        System.out.println("Invalid choice.Please enter a choice between 1 and 5");
       }
       
        switch (option){
         case OPEN_ACCOUNT->
         System.out.println("Open Account - to be implemented in a later lab");
         case DEPOSIT->
         System.out.println("Deposit - to be implemented in a later lab");
         case WITHDRAW->
         System.out.println("Withdraw - to be implemented in a later lab");
         case TRANSFER->
         System.out.println("Transfer - to be implemented in a later lab");
         case EXIT->
         System.out.println("Thank you for using MiniBank. Goodbye!");
          }
}while(choice!=5);
scanner.close();
    }
}


