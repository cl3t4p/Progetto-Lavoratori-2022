package com.cl3t4p.progetto.lavoratori2022.test;

import com.cl3t4p.progetto.lavoratori2022.functions.Mappable;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MappableClass implements Mappable {
    String name;
    long phone;
    LocalDate date;
    List<Float> prices;
}
