package com.cl3t4p.progetto.lavoratori2022.annotation;


import java.lang.annotation.*;


/***
 * This annotation is used to skip the check of a field.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldChecker {
}
