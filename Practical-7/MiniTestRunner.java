
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests {
    @Run
    public void testAdd() {
        System.out.println("Test Add passed");
    }

    @Run
    public void testSub() {
        System.out.println("Test Sub passed");
    }

    public void normalMethod() {
        System.out.println("This method will not run");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        MyTests obj = new MyTests();
        int count = 0;

        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)) {
                method.invoke(obj);
                count++;
            }
        }

        System.out.println("Total tests ran: " + count);
    }
}
