package com.cl3t4p.progetto.lavoratori2022.model.repo;

import com.cl3t4p.progetto.lavoratori2022.model.Dipendente;

public interface DipendenteRepo {

    Dipendente getDipendenteByUserAndPassword(String user, String pass);
}
