package com.cl3t4p.progetto.lavoratori2022.model;

import java.sql.SQLException;
import java.util.List;

public interface PatenteRepo {
    List<String> getPatentiByID(int id);

    void addPatenteByID(int id, String patente) throws SQLException;

    boolean delPatenteByID(int id, String patente);

    List<String> getAllPatenti();
}
