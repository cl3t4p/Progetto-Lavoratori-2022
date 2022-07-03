package com.cl3t4p.progetto.lavoratori2022.model.repo;

import com.cl3t4p.progetto.lavoratori2022.model.Emergenza;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmergenzaRepo {
    int addEmergenza(Emergenza emergenza) throws SQLException;

    int getEmergenzeIDByValues(Map<String, String> emergenza) throws SQLException;

    boolean addEmergenza(Emergenza emergenza, Integer id) throws SQLException;

    boolean checkIfEmergenzaIsLinked(int id_lavoratore, int id_emergenza) throws SQLException;

    List<Emergenza> getEmergenze(int lavoratore_id);

    boolean delEmergenzeByID(int lav_id, Map<String, String> emergenza);
}
