package com.cl3t4p.progettoLavoratori2022;

import com.cl3t4p.progetto.lavoratori2022.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PostDriverTest {

    static PostDriver postDriver;
    static Lavoratore lavoratore;


    @BeforeAll
    static void setup() {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
        lavoratore = new Lavoratore();
        lavoratore.setNome("Testing");
        lavoratore.setCognome("Tester");
        lavoratore.setLuogo_nascita("Bug");
        lavoratore.setData_nascita(Date.valueOf(LocalDate.now()));
        lavoratore.setNazionalita("PC");
        lavoratore.setIndirizzo("BUG");
        lavoratore.setTelefono(4445866977L);
        lavoratore.setEmail("test@bug.com");
        lavoratore.setAutomunito("NO");
        lavoratore.setInizio_disponibile(Date.valueOf(LocalDate.now()));
        lavoratore.setFine_disponibile(Date.valueOf(LocalDate.now()));
        lavoratore.setNome_emergenze("TE");
        lavoratore.setCognome_emergenze("BU");
        lavoratore.setTelefono_emergenze(4445866977L);
        lavoratore.setEmail_emergenze("test@bug.com");
        lavoratore.setId_dipendente(1);
    }


    @DisplayName("Check database connection")
    @Test
    void getConnection() {
        assertDoesNotThrow(() -> postDriver.getConnection());
    }

    @DisplayName("Get comune by partial name")
    @Test
    void getComune() {
        String comune = "aceRn";
        try {
            assertEquals(postDriver.getComuneILike(comune)
                    .get(0)
                    ,"ACERNO","Can get names from the database");

        }catch (SQLException e){
            fail("SQLException error");
        }
    }

    @DisplayName("Get comune by full name")
    @Test
    void getComuneByName() {
        String comune = "acerno";
        try {
            String com = postDriver.getComuniByName(comune);
            if(com == null){
                fail("There is no comune with that name");
            }
            assertEquals(com
                    ,"ACERNO","Can get names from the database");
        }catch (SQLException e){
            fail("SQLException error");
        }
    }
    @DisplayName("Get comune by a wrong full name")
    @Test
    void getComuneByNameWrong() {
        String comune = "ACERNI";
        try {
            assertNull(postDriver.getComuniByName(comune));
        }catch (SQLException e){
            fail("SQLException error");
        }
    }

    @Test
    void getDipendenteByUserAndPassword(){
        String user = "marco01";
        String pass = "12345";
        try {
            Dipendente dipendente = postDriver.getDipendenteByUserAndPassword(user,pass);
            assertEquals(dipendente.getNome(),"Marco");
        }catch (SQLException e){
            fail("SQLException error");
        }
    }

    @Test
    void getLavoratore() {
        try {
            Lavoratore lavoratore = postDriver.getLavoratoreByID(1);
            assertEquals(lavoratore.getNome(),"Marco");
        }catch (SQLException e){
            fail("SQLException error");
        }
    }


    @Test
    @Order(1)
    void addLavoratore(){
        try {
            lavoratore.setId(postDriver.addLavoratore(lavoratore));
        }catch (SQLException e){
            fail("SQLException error");
        }
    }
    @Test
    @Order(2)
    void delLavoratore(){
        try {
            postDriver.removeLavoratore(lavoratore.getId());
            System.out.println(lavoratore.getId());
        }catch (SQLException e){
            fail("SQLException error");
        }
    }


    @Test
    void getPatenti() {

    }
}