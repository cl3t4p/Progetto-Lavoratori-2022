package progetto.database.annotation;

public @interface SQLDInfo {
    String sql_name() default "";
    boolean ignore() default false;
}
