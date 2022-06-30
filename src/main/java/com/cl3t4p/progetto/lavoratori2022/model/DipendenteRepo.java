package com.cl3t4p.progetto.lavoratori2022.model;

import com.cl3t4p.progetto.lavoratori2022.data.type.Dipendente;

public interface DipendenteRepo {

    Dipendente getDipendenteByUserAndPassword(String user, String pass);
}
