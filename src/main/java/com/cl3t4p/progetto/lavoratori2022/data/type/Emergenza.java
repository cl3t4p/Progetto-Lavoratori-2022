package com.cl3t4p.progetto.lavoratori2022.data.type;

import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.data.checks.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emergenza implements ValidateData, Mappable {
    String nome;
    String cognome;
    long telefono;
    String email;
}
