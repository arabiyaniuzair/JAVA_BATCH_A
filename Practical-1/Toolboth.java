import java.util.Scanner;

public class Toolboth {
    record Vehicle(String number, String type) {
    };

    public static void main(String[] args){
         Scanner scanner= new Scanner(System.in);
         int Totaltoll=0;
         int biketotal=0;
         int carcount=0;
         int truckcount=0;
          while (true) {

            System.out.print("Enter vehicle number (or done): ");
            String number = scanner.next();

            if (number.equalsIgnoreCase("done")) {
                break;
            }
             System.out.println("Enter te type of Vehical: ");
             String type= scanner.next().toLowerCase();
              Vehicle v= new Vehicle(number, type);
              int totl=switch(v.type()){
                 case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> 0;
              };
              Totaltoll+=totl;
                 switch (v.type()) {
                case "bike"->biketotal++;
                case "car"->carcount++;
                case "truck"->truckcount++;
            }
        }
        System.out.println("Total bill is: "+Totaltoll);
         if (biketotal >= carcount && biketotal >= truckcount) {
            System.out.println("Most frequent: bike");
        } else if (carcount >= biketotal && carcount >= truckcount) {
            System.out.println("Most frequent: car");
        } else {
            System.out.println("Most frequent: truck");
        }

        scanner.close();
    }


};