package com.cl3t4p.progetto.lavoratori2022.data;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/***
 * This interface is used to convert a class into a map<fieldName, fieldValue.toString()>
 */
public interface Mappable {
    default Map<String,String> toMap(){
        Map<String,String> map = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object obj = field.get(this);
                if (obj == null)
                    map.put(field.getName(),"");
                else
                    map.put(field.getName(),obj.toString());

            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
