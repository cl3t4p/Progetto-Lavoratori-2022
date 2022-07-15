package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

class PostLavoratoreTest {

    Lavoratore lavoratore;
    static PostDriver repo;
    static PostLavoratore postLav;
    private static void loadDBConfig() throws IOException {
        FileReader reader = new FileReader(Main.class.getResource("/test.properties").getPath());
        Properties p = new Properties();
        p.load(reader);
        repo = new PostDriver(p.getProperty("username"), p.getProperty("password"), p.getProperty("db_name"), p.getProperty("host"), Integer.parseInt(p.getProperty("port")));
        postLav = (PostLavoratore) repo.getLavoratoreRepo();
    }

    @BeforeAll
    public static void setUp() throws IOException {
        loadDBConfig();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        repo.getConnection().close();
    }


    @Test
    @Order(1)
    void addLavoratore() throws SQLException {
        lavoratore = new Lavoratore();
        lavoratore.setId_dipendente(1);
        lavoratore.setNome("Test1");
        lavoratore.setCognome("Testing");
        lavoratore.setLuogo_nascita("LAst");
        lavoratore.setData_nascita(Date.valueOf(LocalDate.EPOCH));
        lavoratore.setNazionalita("IT");
        lavoratore.setIndirizzo("DIDID");
        lavoratore.setTelefono(2022233455);
        lavoratore.setEmail("te@te.com");
        lavoratore.setInizio_disponibile(Date.valueOf(LocalDate.EPOCH));
        lavoratore.setFine_disponibile(Date.valueOf(LocalDate.now()));
        int id = postLav.addLavoratore(lavoratore);
        lavoratore.setId(id);
    }

    @Test
    @Order(2)
    void getLavoratoreByID() {
        Lavoratore repoLav = postLav.getLavoratoreByID(lavoratore.getId());
        Assertions.assertEquals(repoLav, lavoratore);
    }

    @Test
    @Order(3)
    void updateLavoratore() throws SQLException {
        Lavoratore tempLav = lavoratore.clone();
        tempLav.setNome("Ale");
        postLav.updateLavoratore(tempLav);
    }


    @Test
    @Order(4)
    void filterWrongLavoratore() {
        FilterBuilder builder = repo.getFilterBuilderInstance();
        builder.addFilter("nome","Test1", FilterBuilder.TypeVar.STRING, FilterBuilder.Logic.AND,false);
        Assertions.assertNotEquals(postLav.filterLavoratore(builder).get(0), lavoratore);
    }

    @Test
    @Order(5)
    void filterLavoratore() {
        FilterBuilder builder = repo.getFilterBuilderInstance();
        builder.addFilter("nome","Ale", FilterBuilder.TypeVar.STRING, FilterBuilder.Logic.AND,false);
        Assertions.assertNotEquals(postLav.filterLavoratore(builder).get(0).getNome(), "Ale");
    }

    @Test
    @Order(6)
    void delLavoratore() {
        postLav.delLavoratore(lavoratore.getId());
    }
}