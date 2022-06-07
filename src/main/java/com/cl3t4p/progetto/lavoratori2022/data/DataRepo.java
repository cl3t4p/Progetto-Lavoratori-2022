package com.cl3t4p.progetto.lavoratori2022.data;

import com.cl3t4p.progetto.lavoratori2022.data.type.Dipendente;

public class DataRepo {


    Dipendente dipendente;
    Integer lavoratore_id;

    public DataRepo() {
    }


    public Integer getLavoratore_id() {
        return 120;
    }

    public void setLavoratore_id(Integer lavoratore_id) {
        this.lavoratore_id = lavoratore_id;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
