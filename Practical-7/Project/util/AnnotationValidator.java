package Project.src.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import Project.src.model.annotation.Positive;
import Project.src.model.annotation.MaxLength;

public class AnnotationValidator {

    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();

        if (obj == null) {
            errors.add("Object cannot be null");
            return errors.toArray(new String[0]);
        }

        Class<?> type = obj.getClass();

        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        if (value instanceof Number
                                && ((Number) value).doubleValue() <= 0) {
                            errors.add(field.getName() + " "
                                    + annotation.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        if (value instanceof String
                                && ((String) value).length()
                                > annotation.value()) {
                            errors.add(field.getName()
                                    + " maximum length is "
                                    + annotation.value());
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Cannot access " + field.getName());
                }
            }

            type = type.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
