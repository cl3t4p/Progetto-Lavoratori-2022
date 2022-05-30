package progetto.data;


import java.lang.annotation.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldChecker {
}
