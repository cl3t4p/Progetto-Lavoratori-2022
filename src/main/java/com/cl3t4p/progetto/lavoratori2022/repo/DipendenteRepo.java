package com.cl3t4p.progetto.lavoratori2022.repo;

import com.cl3t4p.progetto.lavoratori2022.type.Dipendente;

public interface DipendenteRepo {
    Dipendente getDipendenteByUserAndPassword(String user, String pass);
}
