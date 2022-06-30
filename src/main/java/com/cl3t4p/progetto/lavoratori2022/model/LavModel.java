package com.cl3t4p.progetto.lavoratori2022.model;

import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoratore;

public interface LavModel {
    boolean testConnection();
    Lavoratore getLavoratoreByID(int id);
}
