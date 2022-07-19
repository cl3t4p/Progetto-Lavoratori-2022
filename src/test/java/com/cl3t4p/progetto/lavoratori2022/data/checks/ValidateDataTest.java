package com.cl3t4p.progetto.lavoratori2022.data.checks;

import com.cl3t4p.progetto.lavoratori2022.test.ValidateClass;
import org.junit.jupiter.api.*;

@DisplayName("Validate Data")
class ValidateDataTest {

    ValidateClass object;

    @BeforeEach
    void setUp() {
        object = new ValidateClass();
        object.setName("Test");
        object.setOptional("Optional");
        object.setPhone(1545855977);
        object.setEmail("test@test.com");
    }

    @Test
    void valid() {
        Assertions.assertTrue(object.validate());
    }

    @Test
    void invalidNull() {
        object.setName(null);
        Assertions.assertFalse(object.validate());
    }

    @Test
    void invalidPhone() {
        object.setPhone(555545);
        Assertions.assertFalse(object.validate());
    }

    @Test
    void invalidMail() {
        object.setEmail("test@");
        Assertions.assertFalse(object.validate());
    }

    @Test
    void validOptional() {
        object.setOptional(null);
        Assertions.assertTrue(object.validate());
    }
}