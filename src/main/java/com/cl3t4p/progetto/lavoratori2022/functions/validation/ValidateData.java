package com.cl3t4p.progetto.lavoratori2022.functions.validation;


import com.cl3t4p.progetto.lavoratori2022.annotation.RegexCheck;
import com.cl3t4p.progetto.lavoratori2022.annotation.Optional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ValidateData {

    /***
     * This method is used to check if the field is not null or if it's a string is not empty.
     * @return True if every field is valid, false otherwise.
     */
    default boolean validate() {
        for (Field field : getClass().getDeclaredFields()) {
            RegexChecker checker = null;
            field.setAccessible(true);
            boolean ignore = false;
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Optional)
                    ignore = true;

                if (annotation instanceof RegexCheck)
                    checker = ((RegexCheck) annotation).value();
            }


            try {
                Object obj = field.get(this);
                if (obj == null) {
                    //Skip if the field is ignored
                    if(ignore)
                        continue;
                    else
                        return false;
                }
                if (obj instanceof String string)
                    if (string.isEmpty()) {
                        //Skip if the field is ignored
                        if (ignore)
                            continue;
                        return false;
                    }

                if(checker != null)
                    if(!checker.validate(obj.toString()))
                        return false;


            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
