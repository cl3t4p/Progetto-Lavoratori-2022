package com.cl3t4p.progetto.lavoratori2022.model;

import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoro;

import java.sql.SQLException;
import java.util.List;

public interface LavoroRepo {
    int addLavoro(Lavoro lavoro) throws SQLException;

    List<Lavoro> getLavoroByLavID(Integer lavoratore_id);

    boolean deleteLavoroByID(Integer id);
}
