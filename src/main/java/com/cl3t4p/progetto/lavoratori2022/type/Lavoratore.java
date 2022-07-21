package com.cl3t4p.progetto.lavoratori2022.type;


import com.cl3t4p.progetto.lavoratori2022.annotation.RegexCheck;
import com.cl3t4p.progetto.lavoratori2022.annotation.Optional;
import com.cl3t4p.progetto.lavoratori2022.annotation.SQLDInfo;
import com.cl3t4p.progetto.lavoratori2022.functions.Mappable;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoratore implements ValidateData, Mappable,Cloneable {

    @Optional
    int id, id_dipendente;
    String nome, cognome, luogo_nascita;
    Date data_nascita;
    String nazionalita, indirizzo;
    @RegexCheck(RegexChecker.TEL_NUMBER)
    long telefono;
    @RegexCheck(RegexChecker.EMAIL)
    String email;
    String automunito;
    @SQLDInfo(sql_name = "inizio_periodo_disp")
    Date inizio_disponibile;
    @SQLDInfo(sql_name = "fine_periodo_disp")
    Date fine_disponibile;


    @Override
    public Lavoratore clone() {
        try {
            return (Lavoratore) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
