package com.cl3t4p.progetto.lavoratori2022;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dipendente {
    int id;
    String nome;
    String cognome;
    String luogo_nascita;
    Date data_nascita;
    String nazionalita;
    String indirizzo;
    double telefono;
    String email;
    String username;
    String password;
}
