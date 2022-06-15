package com.cl3t4p.progetto.lavoratori2022.database.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLDInfo {
    String sql_name() default "";

    boolean ignore() default false;
}
