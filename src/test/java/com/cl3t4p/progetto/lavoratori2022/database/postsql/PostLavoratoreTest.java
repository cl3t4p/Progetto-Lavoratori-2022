package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.MainRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostLavoratoreTest {


    static Lavoratore lavoratore;
    static MainRepo repo;
    static LavoratoreRepo repoLav;
    static EmbeddedPostgres db;


    @BeforeAll
    static void setUp() {
        repo = setupTempDB();
        repoLav = repo.getLavoratoreRepo();
    }

    @AfterAll
    static void tearDown() throws IOException {
        db.close();
    }

    @SneakyThrows
    private static MainRepo setupTempDB() {
        db = EmbeddedPostgres.builder()
                .start();
        URL url = PostLavoratoreTest.class.getResource("/schema.sql");
        assert url != null;
        String schema = new String(Files.readAllBytes(Path.of(url.toURI())));
        db.getPostgresDatabase().getConnection().createStatement().execute(schema);
        return new PostDriver(db.getPostgresDatabase());
    }


    @Test
    @Order(1)
    void addLavoratore() {
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
        lavoratore.setAutomunito("SI");
        lavoratore.setInizio_disponibile(Date.valueOf(LocalDate.EPOCH));
        lavoratore.setFine_disponibile(Date.valueOf(LocalDate.now()));
        int id = 0;
        try {
            id = repoLav.addLavoratore(lavoratore);
        } catch (SQLException e) {
            Assertions.fail();
        }
        lavoratore.setId(id);
    }

    @Test
    @Order(2)
    void getLavoratoreByID() {
        Lavoratore tmpLav = repoLav.getLavoratoreByID(lavoratore.getId());
        Assertions.assertEquals(tmpLav, lavoratore);
    }

    @Test
    @Order(3)
    void updateLavoratore() {
        Lavoratore tempLav = lavoratore.clone();
        String name = "AL";
        tempLav.setNome(name);
        try {
            repoLav.updateLavoratore(tempLav);
        } catch (SQLException e) {
            Assertions.fail();
        }
        Assertions.assertEquals(repoLav.getLavoratoreByID(lavoratore.getId()).getNome(), name);
    }

    @Test
    @Order(4)
    void filterWrongLavoratore() {
        FilterBuilder builder = repo.getFilterBuilderInstance();
        builder.addFilter("nome", "Test1", FilterBuilder.TypeVar.STRING, FilterBuilder.Logic.AND, false);
        Assertions.assertEquals(repoLav.filterLavoratore(builder).size(), 0);
    }

    @Test
    @Order(5)
    void filterLavoratore() {
        FilterBuilder builder = repo.getFilterBuilderInstance();
        builder.addFilter("nome", "AL", FilterBuilder.TypeVar.STRING, FilterBuilder.Logic.AND, false);
        String name = repoLav.filterLavoratore(builder).get(0).getNome();
        Assertions.assertEquals(name, "AL");
    }

    @Test
    @Order(6)
    void delLavoratore() {
        repoLav.delLavoratore(lavoratore.getId());
        Assertions.assertNull(repoLav.getLavoratoreByID(lavoratore.getId()));
    }
}