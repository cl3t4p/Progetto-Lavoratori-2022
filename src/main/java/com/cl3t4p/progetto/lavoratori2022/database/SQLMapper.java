package com.cl3t4p.progetto.lavoratori2022.database;

import com.cl3t4p.progetto.lavoratori2022.annotation.SQLDInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/***
 * Serialize any java object into SQL Statments
 * Desirialize any SQL Result into java object
 */
public class SQLMapper {

    /**
     * Remember that the order of the serialize is based on the fields names
     * @param statement Statment that need to be modified
     * @param a Object that need be serialized
     * @param fields Names of the field in the class (Not the names on the sql string)
     * @param <A> Class of a
     */
    public static <A> void serializeSQL(PreparedStatement statement, A a, List<String> fields) throws IllegalAccessException, SQLException, NoSuchFieldException {
        int cursor = 0;

        //Get fields based on the name of the give set of fields in order
        for (String field_name : fields) {

            Field field = a.getClass().getDeclaredField(field_name);
            boolean ignore = false;
            //Check if thd field has some additional info with the annotation
            for (Annotation annotation : field.getAnnotations())
                if (annotation instanceof SQLDInfo info) {
                    ignore = info.ignore();
                }
            if (ignore)
                continue;
            cursor++;
            //This will make us possibile to modify private fields in the class
            field.setAccessible(true);
            switch (field.getType().getSimpleName()) {
                case "String" -> statement.setString(cursor, (String) field.get(a));
                case "int" -> statement.setInt(cursor, (int) field.get(a));
                case "double" -> statement.setDouble(cursor, (double) field.get(a));
                case "long" -> statement.setLong(cursor, (Long) field.get(a));
                case "Date" -> statement.setDate(cursor, new Date(((java.util.Date) field.get(a)).getTime()));
                case "LocalDate" -> {
                    LocalDate localDate = LocalDate.MIN;
                    statement.setDate(cursor,
                            new Date(java.util.Date.from(
                                    localDate.atStartOfDay(
                                            ZoneId.systemDefault()).toInstant()).getTime()));
                }
            }
        }
    }

    public static <A> A deserializeSQL(ResultSet result, Class<A> clazz) throws IllegalAccessException, InstantiationException, SQLException {
        //Create a empty instance of A class
        A a;
        try {
            a = clazz.getDeclaredConstructor().newInstance();
        }catch (NoSuchMethodException | InvocationTargetException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //Loop every field in the class
        for (Field field : a.getClass().getDeclaredFields()) {
            String name = field.getName();
            boolean ignore = false;
            //Check if there is a SQLDInfo annotation and it's parameters
            for (Annotation annotation : field.getAnnotations())
                if (annotation instanceof SQLDInfo info) {
                    name = info.sql_name().isEmpty() ? name : info.sql_name();
                    ignore = info.ignore();
                    break;
                }
            if (ignore)
                continue;

            //This will make us possibile to modify private fields in the class
            field.setAccessible(true);
            switch (field.getType().getSimpleName()) {
                case "String" -> field.set(a, result.getString(name));
                case "int" -> field.set(a, result.getInt(name));
                case "double" -> field.set(a, result.getDouble(name));
                case "long" -> field.set(a, result.getLong(name));
                case "Date" -> field.set(a, result.getDate(name));
                case "LocalDate" -> field.set(a, result.getDate(name).toLocalDate());
            }
        }
        return a;
    }

}
