package com.cl3t4p.progetto.lavoratori2022.fx.components.numbertf;





import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Callback;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public abstract class NumberField <A extends Number>  extends TextField {


    public NumberField(Pattern pattern) {
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        setTextFormatter(new TextFormatter<A>(filter));
    }

    public A getValue() {
        if(getText().isEmpty())
            return null;
        try {
            return parseValue(getText());
        } catch (Exception e) {
            return null;
        }
    }

    public abstract A parseValue(String value);
}
