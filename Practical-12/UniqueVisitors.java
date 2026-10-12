import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Arrays;

public class UniqueVisitors {
    public static void main(String[] args) {
        String[] names = {"Uzair", "Rahul", "Amit", "Uzair", "Priya", "Rahul", "Neha"};

        HashSet<String> uniqueUsers = new HashSet<>(Arrays.asList(names));
        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>(Arrays.asList(names));

        System.out.println("Unique count: " + uniqueUsers.size());

        System.out.println("First three distinct names:");

        int count = 0;
        for (String name : orderedUsers) {
            if (count == 3) {
                break;
            }
            System.out.println(name);
            count++;
        }
    }
}