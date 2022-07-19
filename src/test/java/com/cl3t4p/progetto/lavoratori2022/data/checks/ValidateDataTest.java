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
    @DisplayName("Validate Data")
    void valid() {
        Assertions.assertFalse(object.validate());
    }

    @Test
    @DisplayName("Validate if data is null")
    void invalidNull() {
        object.setName(null);
        Assertions.assertFalse(object.validate());
    }

    @Test
    @DisplayName("Validate if phone is valid")
    void invalidPhone() {
        object.setPhone(555545);
        Assertions.assertFalse(object.validate());
    }

    @Test
    @DisplayName("Validate if mail is valid")
    void invalidMail() {
        object.setEmail("test@");
        Assertions.assertFalse(object.validate());
    }

    @Test
    @DisplayName("Validate if optional is valid")
    void validOptional() {
        object.setOptional(null);
        Assertions.assertTrue(object.validate());
    }
}