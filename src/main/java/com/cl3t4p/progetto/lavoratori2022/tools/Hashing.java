package com.cl3t4p.progetto.lavoratori2022.tools;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.Base64;

public class Hashing {

    //TODO Chiedere
    @SneakyThrows
    public static String generateHash(String password) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
    }
}
