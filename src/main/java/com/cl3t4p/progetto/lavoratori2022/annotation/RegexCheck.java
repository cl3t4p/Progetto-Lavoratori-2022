package com.cl3t4p.progetto.lavoratori2022.annotation;

import com.cl3t4p.progetto.lavoratori2022.functions.validation.RegexChecker;

import java.lang.annotation.*;


/**
 * This annotation is used to automatically check a field with a regex.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RegexCheck {
    RegexChecker value();
}
