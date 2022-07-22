package com.cl3t4p.progetto.lavoratori2022.database.postsql;

import com.cl3t4p.progetto.lavoratori2022.repo.ComuneRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.MainRepo;
import com.cl3t4p.progetto.lavoratori2022.test.TestingRepo;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostComuneTest {

    static TestingRepo holder = TestingRepo.getInstance();
    static MainRepo repo = holder.getRepo();
    static ComuneRepo comRepo = repo.getComuneRepo();

    @Test
    void getComuniByName() {
        assertEquals("ROMA", comRepo.getComuniByName("Roma"));
    }

    @Test
    void getComuneILike() {
        List<String> comuni = comRepo.getComuneILike("rom",100);
        assertTrue(comuni.contains("ROMA"));
    }


    @Order(1)
    @Test
    void addComuneByID() {
        comRepo.addComuneByID("roma", 1);
    }

    @Order(2)
    @Test
    void getComuniByID() {
        assertEquals("ROMA", comRepo.getComuniByID(1).get(0));
    }

    @Order(3)
    @Test
    void delComunebyID() {
        comRepo.delComunebyID(1,"ROMA");
        assertEquals(0, comRepo.getComuniByID(1).size());
    }

}