package com.cl3t4p.progetto.lavoratori2022.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResearchCreator {

    final String sql = "SELECT DISTINCT lavoratore.id, lavoratore.nome, lavoratore.cognome " +
            "FROM lavoratore " +
            "INNER JOIN pat_lav ON (lavoratore.id=pat_lav.id_lavoratore) " +
            "INNER JOIN lingua_lav ON (lavoratore.id=lingua_lav.id_lavoratore) " +
            "INNER JOIN lav_comune ON (lavoratore.id=lav_comune.id_lavoratore) " +
            "INNER JOIN esp_lav ON (lavoratore.id=esp_lav.id_lavoratore) WHERE ";
    List<ResearchField> fields = new ArrayList<>();
    boolean isFirst = true;

    public void addFilter(String name, String value, TypeVar type, Logic logic) {
        ResearchField field;
        if (isFirst) {
            isFirst = false;
            field = new ResearchField(name, type, true);
        } else
            field = new ResearchField(name, type, logic);
        field.value = value;
        fields.add(field);
    }

    public String getSQLString() {
        return sql;
    }

    protected PreparedStatement getSQLStatment(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return null;
    }

    public void addStatment(PreparedStatement statement, Field field) {

    }


    private static class ResearchField {
        String name;
        boolean isFirst = false;
        TypeVar typeVar;
        Logic logic;

        String value;

        public ResearchField(String name, TypeVar typeVar, boolean isFirst) {
            this.name = name;
            this.isFirst = isFirst;
            this.logic = null;
        }

        public ResearchField(String name, TypeVar typeVar, Logic logic) {
            this.name = name;
            this.logic = logic;
        }
    }

    public enum Logic {
        AND,
        OR
    }

    public enum TypeVar {
        INT,
        STRING,
        DATE,
    }


}
