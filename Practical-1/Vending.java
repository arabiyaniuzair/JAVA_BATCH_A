import java.util.Scanner;

public class Vending{
enum Coin{
    ONE,
    TWO,
    FIVE,
    TEN
}
public static void main(String[] args){
        int snack=15;
        int total=0;
        Scanner scanner= new Scanner(System.in);
        while(total<snack){
                String userInput=scanner.next().toUpperCase();
                Coin insertCoin=Coin.valueOf(userInput);
                int value=switch(insertCoin){
                  case ONE->1;
                  case TWO->2;
                  case FIVE->5;
                  case TEN->10;

                };
                total+=value;
                
               
                
        }
        int change=total-snack;
        System.out.println("Sanck Price is: " + snack);
        System.out.println("Total Price: " + total);
        System.out.println("Return value is " + change);
scanner.close();
}

};

