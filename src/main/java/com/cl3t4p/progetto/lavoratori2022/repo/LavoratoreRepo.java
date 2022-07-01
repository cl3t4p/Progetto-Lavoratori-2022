package com.cl3t4p.progetto.lavoratori2022.repo;

import com.cl3t4p.progetto.lavoratori2022.data.FilterCreator;
import com.cl3t4p.progetto.lavoratori2022.data.model.Lavoratore;

import java.sql.SQLException;
import java.util.List;

public interface LavoratoreRepo {

    Lavoratore getLavoratoreByID(int id);

    int addLavoratore(Lavoratore lavoratore) throws SQLException;

    void updateLavoratore(Lavoratore lavoratore) throws SQLException;

    boolean delLavoratore(int id);

    List<Lavoratore> filterLavoratore(FilterCreator creator);
}
