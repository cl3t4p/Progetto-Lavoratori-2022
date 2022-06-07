package com.cl3t4p.progetto.lavoratori2022;


import com.cl3t4p.progetto.lavoratori2022.data.CheckData;
import com.cl3t4p.progetto.lavoratori2022.data.FieldChecker;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import com.cl3t4p.progetto.lavoratori2022.database.annotation.SQLDInfo;

import java.sql.SQLException;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoro implements CheckData {

    int id;
    Date inizio_periodo;
    Date fine_periodo;
    String nome_azienda;
    @SQLDInfo(sql_name = "mansione_svolta")
    String mansione;
    @FieldChecker
    @SQLDInfo(sql_name = "luogo_lavoro")
    String luogo;
    @SQLDInfo(sql_name = "retribuzione_lorda_giornaliera")
    int retribuzione;


    public boolean validate() {
        if(!CheckData.super.validate())
            return false;
        try {
            if(Main.getPostDriver().getComuniByName(luogo) == null)
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
