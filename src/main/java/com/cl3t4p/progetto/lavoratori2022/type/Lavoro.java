package com.cl3t4p.progetto.lavoratori2022.type;


import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.annotation.Optional;
import com.cl3t4p.progetto.lavoratori2022.annotation.SQLDInfo;
import com.cl3t4p.progetto.lavoratori2022.functions.Mappable;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.SQLException;
import java.util.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoro implements ValidateData, Mappable {

    @Optional
    int id;
    Date inizio_periodo;
    Date fine_periodo;
    String nome_azienda;
    @SQLDInfo(sql_name = "mansione_svolta")
    String mansione;
    @SQLDInfo(sql_name = "luogo_lavoro")
    String luogo;
    int id_lavoratore;
    //TODO Cambiare e mettere la virgola o fare lo shift di 2 numeri
    @SQLDInfo(sql_name = "retribuzione_lorda_giornaliera")
    int retribuzione;


    public boolean validate() {
        if (!ValidateData.super.validate())
            return false;
        try {
            if (Main.getRepo().getComuneRepo().getComuniByName(luogo) == null)
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
