package com.cl3t4p.progetto.lavoratori2022.database.filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is used to build create a SQL query from a list of filters.
 */
public interface FilterBuilder {


    /**
     * This method is used to add a filter to the query with the default logic.
     * @param name The name of the field.
     * @param value The value of the field.
     * @param type The type of the field (string, int, date, long).
     */
    void addFilter(String name, String value, TypeVar type);


    /**
     * This method is used to add a filter to the query.
     * @param name The name of the field.
     * @param value The value of the field.
     * @param type The type of the field (string, int, date, long).
     * @param logic The logic used to join the filters (AND, OR).
     * @param isSimilar If it's true then it will return values that can contain the data of value
     *                  , otherwise it will return values that are exactly, it's not case sensitive.
     */
    void addFilter(String name, String value, TypeVar type, Logic logic, boolean isSimilar);


    /**
     * This method is used to build the SQL String for the query.
     * @return SQL String;
     */
    String buildSQL();


    /**
     * This method is used to build the readable query for the user.
     */
    String readableString();

    String getReadableAttribute(String name);

    PreparedStatement getSQLStatment(Connection connection) throws SQLException;

    void removeLast();

    boolean isEmpty();

    enum Logic {
        AND('&'),
        OR('|');

        final char readChar;

        Logic(char readChar) {
            this.readChar = readChar;
        }
    }

    enum TypeVar {
        LONG,
        INT,
        STRING,
        DATE

    }
}
