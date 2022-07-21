package com.cl3t4p.progetto.lavoratori2022.fx.components.numbertf;

import java.util.regex.Pattern;

public class DoubleTextField extends NumberField<Double> {


    public DoubleTextField() {
        super(Pattern.compile("-?\\d*([.|,]\\d{0,2})?"));
    }


    @Override
    public Double parseValue(String value) {
        return Double.parseDouble(value);
    }
}
