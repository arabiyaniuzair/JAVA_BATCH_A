class PasswordChecker {
    static boolean lengthrule(String pw){
            return pw.length()>=8;
        }
    static boolean uppercaserule(String pw){
              return pw.matches(".*[A-Z].*");
    }
    static boolean digitrule(String pw){
        return pw.matches(".*[0-9].*");
    }
    static boolean specialcharacterrull(String pw){
        return pw.matches(".*[^A-Za-z0-9].*");
    }
    static String strength(String pw){
        int count=0;
        if(lengthrule(pw)) count++;
        if(uppercaserule(pw)) count++;
        if(digitrule(pw)) count++;
        if(specialcharacterrull(pw)) count++;

        if(count<=1){
            return "Weak";
        }else if(count<=3){
            return "Medium";
        }else{
            return "Strong";
        }
    }
}

public class Regex {
    public static void main(String args[]) {
      String passwords[]={
      "abc",
      "abcdefg",
      "Abcd123",
      "Abcd123!"
   };
   for(String pw:passwords){
    System.out.println("Password: "+pw);
    System.out.println("Length >= 8: "+PasswordChecker.lengthrule(pw));
    System.out.println("Uppercase: "+PasswordChecker.uppercaserule(pw));
    System.out.println("Digit: "+PasswordChecker.digitrule(pw));
    System.out.println("Special character: "+PasswordChecker.specialcharacterrull(pw));
    System.out.println("Strength: "+PasswordChecker.strength(pw));
   }
    }
}
