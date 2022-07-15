package com.cl3t4p.progetto.lavoratori2022.annotation;

import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;

import java.lang.annotation.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RegexCheck {
    RegexChecker value();
}
