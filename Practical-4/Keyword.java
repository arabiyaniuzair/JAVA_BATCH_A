import java.util.Scanner;

class CharFilter {
    static boolean containskeyword(String message, String Keyword) {
        return message.toLowerCase().contains(Keyword.toLowerCase());
    }
}

public class Keyword {
    public static void main(String args[]){
         String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you",
            "10:15 Charlie Good morning",
            "10:20 David"
        };
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the Keyword: ");
    String Keyword=sc.nextLine();
    int count=0;
    StringBuilder report=new StringBuilder();
    for(String lines:logs){
        String[] parts=lines.split(" ",3);
    if (parts.length < 3) {
                continue;
    }
    String time=parts[0];
    String user=parts[1];
    String message=parts[2];
    if(CharFilter.containskeyword(message, Keyword)){
        count++;
        report.append(time)
                      .append(" ")
                      .append(user)
                      .append(": ")
                      .append(message)
                      .append("\n");
    }

    }
    System.out.println("Matchea: "+count);
    System.out.println(report);
    sc.close();
    }
}
