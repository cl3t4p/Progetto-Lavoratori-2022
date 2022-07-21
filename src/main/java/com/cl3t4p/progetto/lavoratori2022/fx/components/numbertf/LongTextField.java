package com.cl3t4p.progetto.lavoratori2022.fx.components.numbertf;


import java.util.regex.Pattern;

public class LongTextField extends NumberField<Long> {

    public LongTextField() {
        super(Pattern.compile("-?\\d*()?"));
    }
    @Override
    public Long parseValue(String value) {
        return Long.parseLong(value);
    }
}
