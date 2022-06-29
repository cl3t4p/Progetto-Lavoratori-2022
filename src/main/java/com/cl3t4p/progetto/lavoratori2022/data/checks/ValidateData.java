package com.cl3t4p.progetto.lavoratori2022.data.checks;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ValidateData {

    /***
     * This method is used to check if the field is not null or if it's a string is not empty.
     * @return True if every field is valid, false otherwise.
     */
    default boolean validate() {
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            boolean ignore = false;
            for (Annotation annotation : field.getAnnotations())
                if (annotation instanceof FieldChecker) {
                    ignore = true;
                    break;
                }
            if (ignore)
                continue;
            try {
                Object obj = field.get(this);
                if (obj == null)
                    return false;
                if (obj instanceof String)
                    if (((String) obj).isEmpty())
                        return false;

            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}