package com.cl3t4p.progetto.lavoratori2022.fx.components.table;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public class MapStringFactory implements Callback<TableColumn.CellDataFeatures<Map<String, String>, String>, ObservableValue<String>> {

    final String key;

    public MapStringFactory(String key) {
        this.key = key;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map<String, String>, String> data) {
        Map<String, String> map = data.getValue();
        return new ReadOnlyStringWrapper(map.get(key));
    }
}
