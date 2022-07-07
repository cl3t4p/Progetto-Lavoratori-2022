package com.cl3t4p.progetto.lavoratori2022.repo;

import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;

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

    FilterBuilder getFilterBuilderInstance();

}
