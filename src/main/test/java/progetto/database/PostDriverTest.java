package progetto.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progetto.Comune;
import progetto.Dipendente;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PostDriverTest {

    static PostDriver postDriver;

    @BeforeAll
    static void setup() {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
    }


    @DisplayName("Check database connection")
    @Test
    void getConnection() {
        assertDoesNotThrow(() -> postDriver.getConnection());
    }

    @DisplayName("Get comune by partial name")
    @Test
    void getComune() {
        String comune = "aceRno";
        try {
            assertEquals(postDriver.getComune(comune)
                    .get(0)
                    .getNome_comune()
                    ,"ACERNO","Can get names from the database");

        }catch (SQLException e){
            fail("SQLException error");
        }
    }

    @DisplayName("Get comune by full name")
    @Test
    void getComuneByName() {
        String comune = "ACERNO";
        try {
            Comune com = postDriver.getComuneByName(comune);
            if(com == null){
                fail("There is no comune with that name");
            }
            assertEquals(com.getNome_comune()
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
            assertNull(postDriver.getComuneByName(comune));
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

}