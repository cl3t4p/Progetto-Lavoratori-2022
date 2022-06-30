package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class APost {
    protected final PostDriver driver;

    public APost(PostDriver postDriver) {
        driver = postDriver;
    }

    protected Connection getConnection() {
        return driver.getConnection();
    }

    protected List<String> getListString(int lavoratore_id, String sql) {
        ResultSet resultSet;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lavoratore_id);
            resultSet = statement.executeQuery();
            List<String> list = new ArrayList<>();
            while (resultSet.next())
                list.add(resultSet.getString(1));
            return list.stream().sorted().toList();
        } catch (SQLException e) {
            return List.of();
        }
    }
}
