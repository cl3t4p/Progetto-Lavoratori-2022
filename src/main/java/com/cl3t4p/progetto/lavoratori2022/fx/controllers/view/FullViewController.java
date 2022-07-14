package com.cl3t4p.progetto.lavoratori2022.fx.controllers.view;

import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class FullViewController implements Initializable {

    List<String> titles;
    List<Map<String, String>> data;

    @FXML
    private TableData view;

    public FullViewController(List<Map<String, String>> list, List<String> titles) {
        this.data = list;
        this.titles = titles;
    }

    public FullViewController(List<Map<String, String>> list) {
        this.data = list;
    }

    private void simpleTable(List<Map<String, String>> list) {
        TableColumn<Map<String, String>, String> column = new TableColumn<>();
        view.getColumns().add(column);
        view.setupColumn(column, "default");
        view.refreshData(list.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(s -> Collections.singletonMap("default", s))
                .toList());
    }


    private void complexTable() {
        titles.stream()
                .collect(Collectors.toMap(s -> s, s -> {
                    TableColumn<Map<String, String>, String> column = new TableColumn<>(s.toUpperCase(Locale.ROOT));
                    view.getColumns().add(column);
                    return column;
                }))
                .forEach((s, t) -> {
                    view.setupColumn(t, s);
                });
        view.refreshData(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (data.isEmpty())
            return;
        if (data.get(0).keySet().size() == 1)
            simpleTable(data);
        else
            complexTable();

    }
}
