package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.SQLMapper;
import com.cl3t4p.progetto.lavoratori2022.repo.EmergenzaRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Emergenza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostEmergenza extends APost implements EmergenzaRepo {

    public PostEmergenza(PostDriver postDriver) {
        super(postDriver);
    }

    @Override
    public int addEmergenza(Emergenza emergenza) throws SQLException {
        int id = getEmergenzeIDByValues(emergenza.toMap());
        if (id == -1) {
            String sql = "INSERT INTO emergenza (nome,cognome,telefono,email) VALUES (?,?,?,?)RETURNING id;";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, emergenza.getNome());
            statement.setString(2, emergenza.getCognome());
            statement.setLong(3, emergenza.getTelefono());
            statement.setString(4, emergenza.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
        }
        return id;
    }

    @Override
    public int getEmergenzeIDByValues(Map<String, String> emergenza) throws SQLException {
        String sql = "SELECT id FROM emergenza WHERE nome=? AND cognome=? AND telefono=? AND email=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, emergenza.get("nome"));
        statement.setString(2, emergenza.get("cognome"));
        statement.setLong(3, Long.parseLong(emergenza.get("telefono")));
        statement.setString(4, emergenza.get("email"));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    @Override
    public boolean addEmergenza(Emergenza emergenza, Integer id) throws SQLException {
        int em_id = addEmergenza(emergenza);

        if (checkIfEmergenzaIsLinked(em_id, id))
            return false;

        String sql = "INSERT INTO emergenza_lav (id_emergenza,id_lavoratore) VALUES (?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, em_id);
        statement.setInt(2, id);
        statement.executeUpdate();
        return true;
    }

    @Override
    public boolean checkIfEmergenzaIsLinked(int id_lavoratore, int id_emergenza) throws SQLException {
        String sql = "SELECT * FROM emergenza_lav WHERE id_lavoratore=? AND id_emergenza=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id_lavoratore);
        statement.setInt(2, id_emergenza);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    @Override
    public List<Emergenza> getEmergenze(int lavoratore_id) {
        String sql = "SELECT emergenza.id,emergenza.nome,emergenza.cognome,emergenza.telefono,emergenza.email FROM emergenza INNER JOIN emergenza_lav ON(emergenza.id=emergenza_lav.id_emergenza) WHERE emergenza_lav.id_lavoratore=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lavoratore_id);
            ResultSet resultSet = statement.executeQuery();
            List<Emergenza> emergenze = new ArrayList<>();
            while (resultSet.next()) {
                try {
                    emergenze.add(SQLMapper.deserializeSQL(resultSet, Emergenza.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return emergenze;
        } catch (SQLException e) {
            return List.of();
        }
    }

    @Override
    public boolean delEmergenzeByID(int lav_id, Map<String, String> emergenza) {
        String sql = "DELETE FROM emergenza_lav WHERE id_lavoratore=? AND id_emergenza=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int eme_id = getEmergenzeIDByValues(emergenza);
            statement.setInt(1, lav_id);
            statement.setInt(2, eme_id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}