package com.cl3t4p.progetto.lavoratori2022;


import com.cl3t4p.progetto.lavoratori2022.data.CheckData;
import com.cl3t4p.progetto.lavoratori2022.data.FieldChecker;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import com.cl3t4p.progetto.lavoratori2022.data.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.database.annotation.SQLDInfo;

import java.sql.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoratore implements CheckData {

    @FieldChecker
    int id,id_dipendente;
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
/*    String nome_emergenze;
    String cognome_emergenze;
    String email_emergenze;
    long telefono_emergenze;*/


    public boolean validate() {
        if(!CheckData.super.validate()){
            return false;
        }
        if(!RegexChecker.EMAIL.validate(email)){
            return false;
        }
        return RegexChecker.TEL_NUMBER.validate(String.valueOf(telefono));
    }
}
