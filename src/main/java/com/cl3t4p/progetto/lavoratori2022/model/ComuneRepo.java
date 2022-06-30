package com.cl3t4p.progetto.lavoratori2022.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ComuneRepo {
    String getComuniByName(String name) throws SQLException;

    boolean delComunebyID(int lav_id, String key);

    void addComuneByID(String comune, int id_lavoratore) throws SQLException;

    List<String> getComuniByID(int lavoratore_id);

    List<String> getComuneILike(String name) throws SQLException;

    List<String> getComuneILike(String name, int limit) throws SQLException;

    List<String> getAllComuni(PreparedStatement statement) throws SQLException;
}
