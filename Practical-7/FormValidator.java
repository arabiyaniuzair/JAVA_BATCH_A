import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    String name;

    @NotBlank
    @MaxLength(30)
    String email;

    @NotBlank
    @MaxLength(10)
    String password;

    SignupForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

public class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                String text = value == null ? "" : value.toString();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (text.trim().isEmpty()) {
                        errors.add(field.getName() + " cannot be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength max = field.getAnnotation(MaxLength.class);

                    if (text.length() > max.value()) {
                        errors.add(field.getName() + " must be at most "
                                + max.value() + " characters");
                    }
                }
            } catch (Exception e) {
                errors.add("Validation error");
            }
        }

        return errors;
    }

    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "verylongemailaddress123456789@example.com",
                "123456789012");

        List<String> errors = validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}