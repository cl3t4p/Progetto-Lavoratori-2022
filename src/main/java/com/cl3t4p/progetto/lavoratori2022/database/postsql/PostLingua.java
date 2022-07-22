package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.LinguaRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostLingua extends APost implements LinguaRepo {


    public PostLingua(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public String getLinguaLike(String name) throws SQLException {
        String sql = "SELECT DISTINCT nome_lingua FROM lingua WHERE nome_lingua ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return "";
        return resultSet.getString(1);
    }

    @Override
    public void addLingua(String lingua) throws SQLException {
        String sql = "INSERT INTO lingua (nome_lingua) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, lingua);
        statement.executeUpdate();
    }

    @Override
    public void addLinguaByID(int id_lavoratore, String lingua) throws SQLException {
        String key = getLinguaLike(lingua);
        if (key.isEmpty()) {
            key = lingua.substring(0, 1).toUpperCase() + lingua.substring(1);
            addLingua(key);
        }
        String sql = "INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, key);
        statement.setInt(2, id_lavoratore);
        statement.executeUpdate();
    }

    @Override
    public boolean delLinguaByID(int lav_id, String key) {
        String sql = "DELETE FROM lingua_lav WHERE ( id_lavoratore = ? AND nome_lingua = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.setString(2, key);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public List<String> getLingueByID(int id) {
        String sql = "SELECT lingua_lav.nome_lingua FROM lavoratore INNER JOIN lingua_lav ON(lavoratore.id=lingua_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }


    protected void delAllLingueByID(int lav_id) {
        String sql = "DELETE FROM lingua_lav WHERE id_lavoratore = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
}