package com.cl3t4p.progettoLavoratori2022;



import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SQLMapperTest  {

    static PostDriver postDriver;

    @BeforeClass
    public static void setUpBeforeClass() {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void serializeSQL() {

    }

    @Test
    void deserializeSQL() {
    }


}