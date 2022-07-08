package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.util.*;

public class FullViewController {


    @FXML
    private TableData view;

    public FullViewController(List<Map<String,String>> list) {
        if (list.isEmpty())
            return;
        if(list.get(0).keySet().size() == 1)
            simpleTable(list);
        else if(list.get(0) instanceof Mappable)
            complexTable(list);
    }

    private void simpleTable(List<Map<String,String>> list) {
        TableColumn<Map, String> column = new TableColumn<>();
        view.setupColumn(column,"default");
        view.refreshData(list.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(s -> Collections.singletonMap("default",s))
                .toList());
    }


    private void complexTable(List<Map<String,String>> list) {
        list.stream()
                .map(Map::keySet)
                .flatMap(Set::stream)
                .forEach(s -> view.setupColumn(new TableColumn<>(s.toUpperCase()),s));
        view.refreshData(list);
    }

}
