package com.cl3t4p.progetto.lavoratori2022.fx.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class DoubleTextField extends TextField {


    public DoubleTextField() {
        Pattern pattern = Pattern.compile("-?\\d*([.|,]\\d{0,2})?");

        UnaryOperator<Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        setTextFormatter(formatter);
    }


    public Double getValue() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
