package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.MainRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

class PostLavoratoreTest {

    PostDriver repo;
    PostLavoratore postLav;
    private void loadDBConfig() throws IOException {
        FileReader reader = new FileReader(getClass().getResource("/test.properties").getPath());
        Properties p = new Properties();
        p.load(reader);
        repo = new PostDriver(p.getProperty("username"), p.getProperty("password"), p.getProperty("db_name"), p.getProperty("host"), Integer.parseInt(p.getProperty("port")));
        postLav = (PostLavoratore) repo.getLavoratoreRepo();
    }

    @Before
    public void setUp() throws IOException {
        loadDBConfig();
    }

    @After
    public void tearDown() throws SQLException {
        repo.getConnection().close();
    }


    @Test
    @Order(1)
    void addLavoratore() {
        Lavoratore lavoratore = new Lavoratore();
        lavoratore.setId_dipendente(1);
    }

    @Test
    @Order(2)
    void getLavoratoreByID() {

    }

    @Test
    @Order(3)
    void updateLavoratore() {
    }


    @Test
    @Order(4)
    void filterLavoratore() {
    }

    @Test
    @Order(5)
    void delLavoratore() {
    }
}