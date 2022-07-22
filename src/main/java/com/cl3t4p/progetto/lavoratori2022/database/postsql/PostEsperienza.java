package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.EsperienzaRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostEsperienza extends APost implements EsperienzaRepo {


    public PostEsperienza(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public String getEspLike(String name) throws SQLException {
        String sql = "SELECT DISTINCT nome_esperienza FROM esperienza WHERE nome_esperienza ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return "";
        return resultSet.getString(1);
    }

    @Override
    public void addEsp(String esp) throws SQLException {
        String sql = "INSERT INTO esperienza (nome_esperienza) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, esp);
        statement.executeUpdate();
    }

    @Override
    public void addEspByID(int id_lavoratore, String esperienza) throws SQLException {
        String key = getEspLike(esperienza);
        if (key.isEmpty()) {
            key = esperienza;
            addEsp(key);
        }
        String sql = "INSERT INTO esp_lav(esperienza,id_lavoratore) VALUES(?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, key);
            statement.setInt(2, id_lavoratore);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean delEspByID(int lav_id, String key) {
        String sql = "DELETE FROM esp_lav WHERE ( id_lavoratore = ? AND esperienza = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.setString(2, key);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<String> getEspByID(int id) {
        String sql = "SELECT esp_lav.esperienza FROM lavoratore INNER JOIN esp_lav ON(lavoratore.id=esp_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }


    protected void dellAllEspByID(int lav_id) {
        String sql = "DELETE FROM esp_lav WHERE id_lavoratore = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
}