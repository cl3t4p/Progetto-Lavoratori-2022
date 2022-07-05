package com.cl3t4p.progetto.lavoratori2022.tools;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class Hashing {

    @SneakyThrows
    public static byte[] generateHash(String password) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(password.getBytes());
    }
}
