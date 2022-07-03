package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.data.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.model.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.SQLMapper;
import com.cl3t4p.progetto.lavoratori2022.model.repo.LavoratoreRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostLavoratore extends APost implements LavoratoreRepo {


    public PostLavoratore(PostDriver driver) {
        super(driver);
    }

    @Override
    public Lavoratore getLavoratoreByID(int id) {
        String sql = "SELECT * FROM lavoratore WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            Lavoratore lavoratore;
            try {
                lavoratore = SQLMapper.deserializeSQL(resultSet, Lavoratore.class);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            return lavoratore;
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public int addLavoratore(Lavoratore lavoratore) throws SQLException {
        String sql = "INSERT INTO lavoratore" +
                "(nome, cognome, luogo_nascita, data_nascita, nazionalita, indirizzo" +
                ", telefono, email, automunito, inizio_periodo_disp, fine_periodo_disp" +
                ",id_dipendente)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";
        List<String> fields = List.of("nome", "cognome", "luogo_nascita", "data_nascita", "nazionalita", "indirizzo",
                "telefono", "email", "automunito",
                "inizio_disponibile", "fine_disponibile", "id_dipendente");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoratore, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ResultSet set = statement.executeQuery();
        set.next();
        return set.getInt(1);
    }

    @Override
    public void updateLavoratore(Lavoratore lavoratore) throws SQLException {
        String sql = "UPDATE lavoratore SET " +
                "nome=?, cognome=?, luogo_nascita=?, data_nascita=?, nazionalita=?, indirizzo=?, telefono=?, email=?, automunito=?, inizio_periodo_disp=?, fine_periodo_disp=? WHERE id=?";
        List<String> fields = List.of("nome", "cognome", "luogo_nascita", "data_nascita", "nazionalita", "indirizzo",
                "telefono", "email", "automunito",
                "inizio_disponibile", "fine_disponibile");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoratore, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        statement.setInt(12, lavoratore.getId());
        statement.executeUpdate();
    }

    @Override
    public boolean delLavoratore(int id) {
        String sql = "DELETE FROM lavoratore WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Lavoratore> filterLavoratore(FilterBuilder creator) {
        try (PreparedStatement statement = creator.getSQLStatment(getConnection())) {
            ResultSet resultSet = statement.executeQuery();
            List<Lavoratore> lavoratori = new ArrayList<>();
            while (resultSet.next()) {
                try {
                    lavoratori.add(SQLMapper.deserializeSQL(resultSet, Lavoratore.class));
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
            return lavoratori;
        } catch (SQLException ignore) {
            return List.of();
        }
    }
}
