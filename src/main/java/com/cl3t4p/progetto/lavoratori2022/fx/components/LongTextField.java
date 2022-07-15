package com.cl3t4p.progetto.lavoratori2022.fx.components;


import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class LongTextField extends TextField {


    public LongTextField() {
        Pattern pattern = Pattern.compile("-?\\d*()?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        setTextFormatter(formatter);
    }

    public Long getValue() {
        if(getText().isEmpty())
            return null;
        try {
            return Long.parseLong(getText());
        } catch (Exception e) {
            return null;
        }

    }
}
