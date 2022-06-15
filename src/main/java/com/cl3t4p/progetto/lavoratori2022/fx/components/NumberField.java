package com.cl3t4p.progetto.lavoratori2022.fx.components;


import javafx.scene.control.TextField;

public class NumberField extends TextField {
    public NumberField() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    public Long getValue() {
        try {
            return Long.parseLong(getText());
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
