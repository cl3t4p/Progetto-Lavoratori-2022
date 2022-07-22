package com.cl3t4p.progetto.lavoratori2022.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ComuneRepo {
    String getComuniByName(String name);

    boolean delComunebyID(int lav_id, String key);

    boolean addComuneByID(String comune, int id_lavoratore);

    List<String> getComuniByID(int lavoratore_id);

    List<String> getComuneILike(String name);

    List<String> getComuneILike(String name, int limit);

    List<String> getAllComuni(PreparedStatement statement) throws SQLException;
}
