package progetto.database;

import progetto.database.annotation.SQLDInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

/***
 * Serialize any java object into SQL Statments
 * Desirialize any SQL Result into java object
 */
public class SQLMapper {

    /***
     * Remember that the order of the serialize is based on the fields names
     * @param statement Statment that need to be modified
     * @param a Object that need be serialized
     * @param fields Names of the field in the class (Not the names on the sql string)
     * @param <A> Class of a
     */
    public static <A> void serializeSQL(PreparedStatement statement, A a, Set<String> fields) throws IllegalAccessException, SQLException, NoSuchFieldException {
        int cursor = 1;
        for (String field_name : fields) {
            Field field = a.getClass().getField(field_name);
            boolean ignore = false;
            for (Annotation annotation : field.getAnnotations())
                if (annotation instanceof SQLDInfo) {
                    SQLDInfo info = (SQLDInfo) annotation;
                    ignore = info.ignore();
                }
            if (ignore)
                continue;
            field.setAccessible(true);
            switch (field.getType().getSimpleName()) {
                case "String":
                    statement.setString(cursor, (String) field.get(a));
                    break;
                case "int":
                    statement.setInt(cursor, (int) field.get(a));
                    break;
                case "Date":
                    statement.setDate(cursor, new Date(((java.util.Date) field.get(a)).getTime()));
                    break;
                case "LocalDate":
                    LocalDate localDate = LocalDate.MIN;
                    statement.setDate(cursor,
                            new Date(java.util.Date.from(
                                    localDate.atStartOfDay(
                                            ZoneId.systemDefault()).toInstant()).getTime()));
                    break;
            }
        }
    }
    public static <A> A deserializeSQL(ResultSet result, Class<A> clazz) throws IllegalAccessException, InstantiationException, SQLException {
        A a = clazz.newInstance();
        for (Field field : a.getClass().getDeclaredFields()) {
            String name = field.getName();
            boolean ignore = false;
            for (Annotation annotation : field.getAnnotations())
                if(annotation instanceof SQLDInfo){
                    SQLDInfo info = (SQLDInfo) annotation;
                    name = info.sql_name().isEmpty() ? name : info.sql_name();
                    ignore = info.ignore();
                }
            if(ignore)
                continue;
            field.setAccessible(true);
            switch (field.getType().getSimpleName()){
                case "String":
                    field.set(a,result.getString(name));
                    break;
                case "int":
                    field.set(a,result.getInt(name));
                    break;
                case "Date":
                    field.set(a,result.getDate(name));
                    break;
                case "LocalDate":
                    field.set(a,result.getDate(name).toLocalDate());
                    break;
            }
        }
        return a;
    }

}
