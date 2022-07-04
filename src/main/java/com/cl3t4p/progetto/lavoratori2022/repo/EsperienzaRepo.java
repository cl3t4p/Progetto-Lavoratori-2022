package com.cl3t4p.progetto.lavoratori2022.repo;

import java.sql.SQLException;
import java.util.List;

public interface EsperienzaRepo {
    String getEspLike(String name) throws SQLException;

    void addEsp(String esp) throws SQLException;

    void addEspByID(int id_lavoratore, String esperienza) throws SQLException;

    boolean delEspByID(int lav_id, String key);

    List<String> getEspByID(int id);
}
