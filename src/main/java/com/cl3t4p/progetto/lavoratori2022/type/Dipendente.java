package com.cl3t4p.progetto.lavoratori2022.type;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dipendente {
    private int id;
    private String nome;
    private String cognome;
    private String luogo_nascita;
    private Date data_nascita;
    private String nazionalita;
    private String indirizzo;
    private double telefono;
    private String email;
    private String username;
    private String password;

}
