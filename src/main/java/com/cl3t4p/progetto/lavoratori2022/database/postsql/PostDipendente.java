package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.data.model.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.SQLMapper;
import com.cl3t4p.progetto.lavoratori2022.repo.DipendenteRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDipendente extends APost implements DipendenteRepo {

    public PostDipendente(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public Dipendente getDipendenteByUserAndPassword(String user, String pass) {
        String sql = "SELECT * FROM dipendente WHERE username=? and password=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                try {
                    return (SQLMapper.deserializeSQL(resultSet, Dipendente.class));
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
