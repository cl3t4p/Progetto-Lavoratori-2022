package com.cl3t4p.progetto.lavoratori2022.database;

import com.cl3t4p.progetto.lavoratori2022.model.Dipendente;
import lombok.Getter;
import lombok.Setter;

/***
 * Repo that contains temporary data for the application.
 */
@Getter
@Setter
public class DataRepo {


    Dipendente dipendente;
    Integer lavoratore_id;


    //TODO Remove
    public DataRepo() {
        this.lavoratore_id = 58;
    }
}
