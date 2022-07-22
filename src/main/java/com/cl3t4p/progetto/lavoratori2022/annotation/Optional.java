package com.cl3t4p.progetto.lavoratori2022.annotation;


import java.lang.annotation.*;


/**
 * This annotation is used to make a field optional.
 * If the field is null, or if it's a empty string, the field is considered as valid.
 * This annotation can be used with RegexCheck
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface  Optional {

}
