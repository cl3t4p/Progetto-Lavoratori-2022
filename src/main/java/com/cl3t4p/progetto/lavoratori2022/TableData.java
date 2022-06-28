package com.cl3t4p.progetto.lavoratori2022;

import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TableData {
    private final ObservableList<Map<String, String>> list = FXCollections.observableArrayList();

    private final TableView<Map<String, String>> view;
    private final ButtonColumn<Map<String, String>> buttonColumn;
    private final Supplier<List<Map<String, String>>> supplier;


    public TableData(TableView<Map<String, String>> view, ButtonColumn<Map<String,String>> buttonColumn, Supplier<List<Map<String,String>>> supplier) {
        this.supplier = supplier;
        this.buttonColumn = buttonColumn;
        this.view = view;
        view.getColumns().add(buttonColumn);
        view.setItems(list);
    }

    public void refreshData(List<Map<String,String>> data) {
        list.clear();
        list.addAll(data);
    }
    public void refreshData(){
        refreshData(supplier.get());
    }


    public void setupColumn(TableColumn<Map, String> column, String key) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(new MapValueFactory<>(key));

        column.prefWidthProperty().bind(view.widthProperty().subtract(buttonColumn.widthProperty()).subtract(2).divide(view.getColumns().size()-1));
    }



    public static List<Map<String,String>> toMap(List<? extends Mappable> mappables){
        return mappables.stream().map(Mappable::toMap).toList();
    }


}
