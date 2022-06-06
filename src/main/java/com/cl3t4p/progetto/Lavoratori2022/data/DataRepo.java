package com.cl3t4p.progetto.Lavoratori2022.data;

import com.cl3t4p.progetto.Lavoratori2022.Dipendente;

public class DataRepo {


    Dipendente dipendente;
    Integer lavoratore_id;

    public DataRepo() {
    }


    public Integer getLavoratore_id() {
        return 58;
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
