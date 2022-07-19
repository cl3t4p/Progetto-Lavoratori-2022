package com.cl3t4p.progetto.lavoratori2022.data;

import com.cl3t4p.progetto.lavoratori2022.test.MappableClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MappableTest {

    MappableClass object;
    Map<String,String> map;

    @BeforeEach
    void setUp() {
        object = new MappableClass();
        object.setName("Test");
        object.setPhone(123456789);
        object.setDate(LocalDate.now());
        object.setPrices(List.of(3.4f, 5.6f, 7.8f));
        map = object.toMap();
    }

    @Test
    void stringMap() {
        assertEquals(map.get("name"),object.getName());
    }

    @Test
    void numberMap() {
        assertEquals(map.get("phone"),String.valueOf(object.getPhone()));
    }

    @Test
    void complexMap() {
        assertEquals(map.get("date"),object.getDate().toString());
    }

    @Test
    void listMap() {
        assertEquals(map.get("prices"),object.getPrices().toString());
    }

    @Test
    void nullTest(){
        object.setName(null);
        map = object.toMap();
        assertTrue(map.get("name").isEmpty());
    }

}