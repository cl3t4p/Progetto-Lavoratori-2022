package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.ComuneRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostComune extends APost implements ComuneRepo {
    public PostComune(PostDriver postDriver) {
        super(postDriver);
    }


    @Override
    public String getComuniByName(String name) {
        String sql = "SELECT DISTINCT * FROM comune WHERE nome_comune ILIKE ?";
        try(
        PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name.toUpperCase(Locale.ROOT));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            return resultSet.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean delComunebyID(int lav_id, String key) {
        String sql = "DELETE FROM lav_comune WHERE ( id_lavoratore = ? AND comune = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.setString(2, key.toUpperCase());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addComuneByID(String comune, int id_lavoratore) {
        String sql = "INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, comune.toUpperCase());
            statement.setInt(2, id_lavoratore);
            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<String> getComuniByID(int lavoratore_id) {
        String sql = "SELECT lav_comune.comune FROM lavoratore INNER JOIN lav_comune ON(lavoratore.id=lav_comune.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(lavoratore_id, sql);
    }

    @Override
    public List<String> getComuneILike(String name) {
        return getComuneILike(name, 20);
    }

    @Override
    public List<String> getComuneILike(String name, int limit) {
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE concat('%', ?, '%') LIMIT ?";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name.toUpperCase(Locale.ROOT));
            statement.setInt(2, limit);
            return getAllComuni(statement);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getAllComuni(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next())
            list.add(resultSet.getString(1));
        return list.stream().sorted().toList();
    }


    protected void dellAllComuniByID(int lav_id) {
        String sql = "DELETE FROM lav_comune WHERE id_lavoratore = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
}