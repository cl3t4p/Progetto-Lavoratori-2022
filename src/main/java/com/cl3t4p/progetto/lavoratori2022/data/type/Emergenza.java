package com.cl3t4p.progetto.lavoratori2022.data.type;

import com.cl3t4p.progetto.lavoratori2022.data.checks.ValidateData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emergenza implements ValidateData {
    String nome;
    String cognome;
    long telefono;
    String email;


    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("nome", nome);
        map.put("cognome", cognome);
        map.put("telefono", String.valueOf(telefono));
        map.put("email", email);
        return map;
    }
}
