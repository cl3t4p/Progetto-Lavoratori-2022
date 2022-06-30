package com.cl3t4p.progetto.lavoratori2022.database;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;



public class ResearchCreator {

    final String sql = "SELECT DISTINCT lavoratore.id, lavoratore.nome, lavoratore.cognome " +
            "FROM lavoratore " +
            "INNER JOIN pat_lav ON (lavoratore.id=pat_lav.id_lavoratore) " +
            "INNER JOIN lingua_lav ON (lavoratore.id=lingua_lav.id_lavoratore) " +
            "INNER JOIN lav_comune ON (lavoratore.id=lav_comune.id_lavoratore) " +
            "INNER JOIN esp_lav ON (lavoratore.id=esp_lav.id_lavoratore) WHERE ";

    final List<ResearchField> fields = new ArrayList<>();



    public void addFilter(String name, String value, TypeVar type, Logic logic,boolean isSimilar) {
        ResearchField field;
        Optional<ResearchField> opt_field = fields.stream()
                .filter(f -> f.name.equals(name))
                .findFirst();

        if(opt_field.isPresent()){
            field = opt_field.get();
            field.typeVar = type;
            field.logic = logic;
            field.isSimilar = isSimilar;
        }else{
            field = new ResearchField(name,type,logic,isSimilar);
        }
        field.value = value;
        fields.add(field);
    }

    private String buildSQL(){
        StringBuilder builder = new StringBuilder();
        ArrayList<ResearchField> list = new ArrayList<>(fields);
        if(list.size() == 0)
            return "";
        builder.append(list.remove(0).toFirstSQL());
        for (ResearchField researchField : list) {
            builder.append(researchField.toSQL());
        }
        return builder.toString();
    }



    protected PreparedStatement getSQLStatment(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql+buildSQL());
        for (int i = 0; i < fields.size(); i++) {
            setStatments(statement,fields.get(i),i+1);
        }
        return statement;
    }

    private void setStatments(PreparedStatement statement,ResearchField field,int cursor) throws SQLException {
        switch(field.typeVar){
            case LONG:
                statement.setLong(cursor,Long.parseLong(field.value));
                break;
            case INT:
                statement.setInt(cursor,Integer.parseInt(field.value));
                break;
            case STRING:
                statement.setString(cursor,field.value);
                break;
            case DATE:
                statement.setDate(cursor, Date.valueOf(field.value));
                break;
        }
    }



    private static class ResearchField {
        String name;
        TypeVar typeVar;
        Logic logic;
        String value;
        boolean isSimilar;

        public ResearchField(String name, TypeVar typeVar, Logic logic,boolean isSimilar) {
            this.isSimilar = isSimilar;
            this.name = name;
            this.logic = logic;
            this.typeVar = typeVar;
        }

        private String toSQL(){
            return " " + logic.name() + toFirstSQL();
        }
        //TODO Valori aggingi

        private String toFirstSQL(){
            if(isSimilar)
                return  " " +name + " ILIKE ?";
            return " "+name;
        }

    }

    public enum Logic {
        AND,
        OR
    }

    public enum TypeVar {
        LONG,
        INT,
        STRING,
        DATE;

    }


}
