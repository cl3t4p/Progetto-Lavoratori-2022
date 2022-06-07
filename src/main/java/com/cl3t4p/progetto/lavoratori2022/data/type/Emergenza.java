package com.cl3t4p.progetto.lavoratori2022.data.type;

import com.cl3t4p.progetto.lavoratori2022.data.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emergenza implements ValidateData {
    String nome;
    String cognome;
    long telefono;
    String email;
}
