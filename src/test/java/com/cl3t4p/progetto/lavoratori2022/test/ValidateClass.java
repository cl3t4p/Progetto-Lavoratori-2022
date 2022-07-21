package com.cl3t4p.progetto.lavoratori2022.test;

import com.cl3t4p.progetto.lavoratori2022.annotation.RegexCheck;
import com.cl3t4p.progetto.lavoratori2022.annotation.Optional;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.ValidateData;
import lombok.Data;

@Data
public class ValidateClass implements ValidateData {
    String name;
    @RegexCheck(RegexChecker.TEL_NUMBER)
    long phone;
    @RegexCheck(RegexChecker.EMAIL)
    String email;
    @Optional
    String optional;
    @RegexCheck(RegexChecker.EMAIL)
    @Optional
    String second_email;

}
