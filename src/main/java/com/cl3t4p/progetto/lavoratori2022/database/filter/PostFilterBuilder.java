package com.cl3t4p.progetto.lavoratori2022.database.filter;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
public class PostFilterBuilder implements FilterBuilder {

    private Logic defaultLogic = Logic.OR;
    private boolean defaultSimilar = true;


    //Base query
    final String sql = "SELECT DISTINCT * FROM lavoratore " +
            "LEFT JOIN pat_lav ON (lavoratore.id=pat_lav.id_lavoratore) " +
            "LEFT JOIN lingua_lav ON (lavoratore.id=lingua_lav.id_lavoratore) " +
            "LEFT JOIN lav_comune ON (lavoratore.id=lav_comune.id_lavoratore) " +
            "LEFT JOIN esp_lav ON (lavoratore.id=esp_lav.id_lavoratore) " +
            "WHERE ";

    final List<ResearchField> fields = new ArrayList<>();


    @Override
    public void addFilter(String name, String value, TypeVar type) {
        addFilter(name, value, type, defaultLogic, defaultSimilar);
    }


    @Override
    public void addFilter(String name, String value, TypeVar type, Logic logic, boolean isSimilar) {
        if (value == null)
            return;
        if (value.isEmpty() || value.isBlank())
            return;
        ResearchField field = new ResearchField(name, type, logic, isSimilar);
        field.value = value;
        if (fields.stream().anyMatch(field::equals))
            return;
        fields.add(field);
    }

    /**
     * This method will create a clone of the list of filters.
     *
     * @return A clone of the list of filters.
     */
    private List<ResearchField> getListClone() {
        ArrayList<ResearchField> list = new ArrayList<>(fields.size());
        for (ResearchField field : fields) {
            list.add(field.clone());
        }
        return list;
    }


    @Override
    public String buildSQL() {
        StringBuilder builder = new StringBuilder();
        List<ResearchField> list = getListClone();
        if (list.size() == 0)
            return "";
        builder.append(list.remove(0).toFirstSQL());
        for (ResearchField researchField : list) {
            builder.append(researchField.toSQL());
        }
        return builder.toString();
    }


    @Override
    public String readableString() {
        List<ResearchField> list = getListClone();
        if (list.size() == 0)
            return "";
        return list.stream()
                .map(ResearchField::getName)
                .distinct()
                .map(this::getReadableAttribute)
                .collect(Collectors.joining(";"));
    }


    @Override
    public String getReadableAttribute(String name) {
        String string = fields.stream()
                .filter(f -> f.name.equals(name))
                .map(ResearchField::toReadForm)
                .collect(Collectors.joining(","));
        return name + ":(" + string + ")";
    }


    @Override
    public PreparedStatement getSQLStatment(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql + buildSQL());
        for (int i = 0; i < fields.size(); i++) {
            setStatments(statement, fields.get(i), i + 1);
        }
        return statement;
    }

    private void setStatments(PreparedStatement statement, ResearchField field, int cursor) throws SQLException {
        switch (field.typeVar) {
            case LONG -> statement.setLong(cursor, Long.parseLong(field.value));
            case INT -> statement.setInt(cursor, Integer.parseInt(field.value));
            case STRING -> statement.setString(cursor, field.value);
            case DATE -> statement.setDate(cursor, Date.valueOf(field.value));
        }
    }

    @Override
    public void removeLast() {
        fields.remove(fields.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return fields.isEmpty();
    }

    @Getter
    @EqualsAndHashCode
    private static class ResearchField implements Cloneable {
        String name;
        TypeVar typeVar;
        Logic logic;
        String value;
        boolean isSimilar;

        public ResearchField(String name, TypeVar typeVar, Logic logic, boolean isSimilar) {
            this.isSimilar = isSimilar;
            this.name = name;
            this.logic = logic;
            this.typeVar = typeVar;
        }

        private String toSQL() {
            return " " + logic.name() + toFirstSQL();
        }


        private String toFirstSQL() {
            if (typeVar.equals(TypeVar.DATE)) {
                return " ? BETWEEN lavoratore.inizio_periodo_disp AND lavoratore.fine_periodo_disp";
            }
            if (isSimilar)
                return " " + name + " ILIKE concat('%', ?, '%') ";
            return " " + name + " ILIKE ?";
        }

        public String toReadForm() {
            StringBuilder builder = new StringBuilder();
            builder.append(logic.readChar);

            if (isSimilar)
                builder.append("%");
            builder.append("'")
                    .append(value)
                    .append("'");
            return builder.toString();
        }

        @Override
        public ResearchField clone() {
            try {
                return (ResearchField) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }


    }


}
