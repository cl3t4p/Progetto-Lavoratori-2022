package com.cl3t4p.progetto.lavoratori2022.test;

import com.cl3t4p.progetto.lavoratori2022.annotation.RegexCheck;
import com.cl3t4p.progetto.lavoratori2022.annotation.SkipCheck;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.data.checks.ValidateData;
import lombok.Data;

@Data
public class ValidateClass implements ValidateData {
    String name;
    @RegexCheck(RegexChecker.TEL_NUMBER)
    long phone;
    @RegexCheck(RegexChecker.EMAIL)
    String email;
    @SkipCheck
    String optional;

}
