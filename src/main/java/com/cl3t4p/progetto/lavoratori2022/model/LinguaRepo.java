package com.cl3t4p.progetto.lavoratori2022.model;

import java.sql.SQLException;
import java.util.List;

public interface LinguaRepo {
    String getLinguaLike(String name) throws SQLException;

    void addLingua(String lingua) throws SQLException;

    void addLinguaByID(int id_lavoratore, String lingua) throws SQLException;

    boolean delLinguaByID(int lav_id, String key);

    List<String> getLingueByID(int id);
}
