package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.data.model.Lavoro;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.SQLMapper;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoroRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostLavoro extends APost implements LavoroRepo {


    public PostLavoro(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public int addLavoro(Lavoro lavoro) throws SQLException {
        String sql = "INSERT INTO lavoro_svolto " +
                "(inizio_periodo,fine_periodo,nome_azienda,mansione_svolta,luogo_lavoro,retribuzione_lorda_giornaliera ,id_lavoratore) "
                +
                "VALUES (?,?,?,?,?,?,?)RETURNING id;";
        List<String> fields = List.of("inizio_periodo", "fine_periodo", "nome_azienda", "mansione", "luogo",
                "retribuzione", "id_lavoratore");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoro, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            return resultSet.getInt(1);
        return -1;
    }

    @Override
    public List<Lavoro> getLavoroByLavID(Integer lavoratore_id) {
        String sql = "SELECT * FROM lavoro_svolto WHERE id_lavoratore=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lavoratore_id);
            ResultSet resultSet = statement.executeQuery();
            List<Lavoro> lavori = new ArrayList<Lavoro>();
            while (resultSet.next()) {
                try {
                    lavori.add(SQLMapper.deserializeSQL(resultSet, Lavoro.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return lavori;
        } catch (SQLException e) {
            return List.of();
        }
    }

    @Override
    public boolean deleteLavoroByID(Integer id) {
        String sql = "DELETE FROM lavoro_svolto WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}