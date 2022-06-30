package com.cl3t4p.progetto.lavoratori2022.model;

import java.sql.Connection;

public interface MainRepo {

    Connection getConnection();
    boolean testConnection();
    ComuneRepo getComuneRepo();
    EmergenzaRepo getEmergenzaRepo();
    EsperienzaRepo getEsperienzaRepo();
    LavoratoreRepo getLavoratoreRepo();
    LavoroRepo getLavoroRepo();
    LinguaRepo getLinguaRepo();
    PatenteRepo getPatenteRepo();
    DipendenteRepo getDipendenteRepo();

}
