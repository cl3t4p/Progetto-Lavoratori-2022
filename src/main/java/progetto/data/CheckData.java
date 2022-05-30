package progetto.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface CheckData {
    default boolean validate() {
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            boolean ignore = false;
            for (Annotation annotation : field.getAnnotations())
                if (annotation instanceof FieldChecker) {
                    ignore = true;
                    break;
                }
            if(ignore)
                continue;
            try {
                if(field.get(this) == null)
                    return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
