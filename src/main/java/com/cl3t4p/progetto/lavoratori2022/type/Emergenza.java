package com.cl3t4p.progetto.lavoratori2022.type;

import com.cl3t4p.progetto.lavoratori2022.annotation.RegexCheck;
import com.cl3t4p.progetto.lavoratori2022.functions.Mappable;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emergenza implements ValidateData, Mappable {
    String nome;
    String cognome;
    @RegexCheck(RegexChecker.TEL_NUMBER)
    long telefono;
    @RegexCheck(RegexChecker.EMAIL)
    String email;
}
