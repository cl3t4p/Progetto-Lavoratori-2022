package com.cl3t4p.progetto.lavoratori2022.data.model;


import com.cl3t4p.progetto.lavoratori2022.annotation.FieldChecker;
import com.cl3t4p.progetto.lavoratori2022.annotation.SQLDInfo;
import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.data.checks.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoratore implements ValidateData, Mappable {

    @FieldChecker
    int id, id_dipendente;
    String nome, cognome, luogo_nascita;
    Date data_nascita;
    String nazionalita, indirizzo;
    @FieldChecker
    long telefono;
    @FieldChecker
    String email;
    String automunito;
    @SQLDInfo(sql_name = "inizio_periodo_disp")
    Date inizio_disponibile;
    @SQLDInfo(sql_name = "fine_periodo_disp")
    Date fine_disponibile;


    public boolean validate() {
        if (!ValidateData.super.validate()) {
            return false;
        }
        if (!RegexChecker.EMAIL.validate(email)) {
            return false;
        }
        return RegexChecker.TEL_NUMBER.validate(String.valueOf(telefono));
    }
}
