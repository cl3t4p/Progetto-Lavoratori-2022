package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.PatenteRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostPatente extends APost implements PatenteRepo {


    public PostPatente(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public List<String> getPatentiByID(int id) {
        String sql = "SELECT pat_lav.nome_patente FROM lavoratore INNER JOIN pat_lav ON(lavoratore.id=pat_lav.id_lavoratore) WHERE lavoratore.id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<String> patenti = new ArrayList<>();
            while (resultSet.next())
                patenti.add(resultSet.getString(1));
            return patenti;
        } catch (SQLException e) {
            return List.of();
        }

    }

    @Override
    public void addPatenteByID(int id, String patente) throws SQLException {
        String sql = "INSERT INTO pat_lav (id_lavoratore, nome_patente) VALUES (?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, patente);
        statement.executeUpdate();
    }

    @Override
    public boolean delPatenteByID(int id, String patente) {
        String sql = "DELETE FROM pat_lav WHERE ( id_lavoratore = ? AND nome_patente = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, patente);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<String> getAllPatenti() {
        String sql = "SELECT * FROM patente";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);) {
            ResultSet resultSet = statement.executeQuery();
            List<String> patenti = new ArrayList<>();
            while (resultSet.next())
                patenti.add(resultSet.getString(1));
            return patenti.stream().sorted().toList();
        } catch (SQLException ignored) {
            return List.of();
        }
    }
}
