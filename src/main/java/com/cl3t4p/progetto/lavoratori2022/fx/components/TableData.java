package com.cl3t4p.progetto.lavoratori2022.fx.components;

import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.util.*;
import java.util.function.Supplier;


/***
 * TableData help create and manage a table view that can be used to display a list of objects or a list of maps.
 */
public class TableData extends TableView<Map<String, String>> {
    private final ObservableList<Map<String, String>> list = FXCollections.observableArrayList();

    private Supplier<List<Map<String, String>>> supplier;


    private final List<Double> customSize = new ArrayList<>();

    public TableData() {
        setItems(list);
    }

    public void setSupplier(Supplier<List<Map<String, String>>> supplier) {
        this.supplier = supplier;
    }
    public void setButtonColumn(ButtonColumn buttonColumn) {
        getColumns().add(buttonColumn);
        customSize.add(buttonColumn.getWidth());
    }

    /**
     * Update the table view with the list of maps.
     */
    public void refreshData(List<Map<String,String>> data) {
        list.clear();
        list.addAll(data);
    }

    /**
     * Update the table view with the list called by the supplier.
     */
    public void refreshData(){
        refreshData(supplier.get());
    }


    /**
     * Prepare a column for the table view.
     * @param column the column to prepare
     * @param key The key of the map to use as value for the column.
     */
    public void setupColumn(TableColumn<Map, String> column, String key) {
        setupColumnOpt(column, key);
        column.prefWidthProperty().bind(
                widthProperty()
                        .subtract(3)
                        .subtract(customSize.stream().mapToDouble(Double::doubleValue).sum())
                        .divide(getColumns().size()-customSize.size()));
    }
    public void setupColumn(TableColumn<Map, String> column, String key,int size) {
        setupColumnOpt(column, key);
        if(size != -1)
            column.setPrefWidth(size);
        customSize.add(column.getWidth());
    }

    private void setupColumnOpt(TableColumn<Map, String> column, String key) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(new MapValueFactory<>(key));
        refreshData();
    }


    public void addSelectionListener(ChangeListener <? super Map<String, String>> listener) {
        getSelectionModel().selectedItemProperty().addListener(listener);
    }


    /**
     * Get the list of maps from a mappable objects.
     * @param mappables the list of mappable objects
     * @return the list of maps
     */
    public static List<Map<String,String>> toMap(List<? extends Mappable> mappables){
        return mappables.stream().map(Mappable::toMap).toList();
    }

    /**
     * Return the list of maps with the same name of the columns.
     * @param name the name of the columns
     * @param list the list of mappable objects
     * @return the list of maps
     */
    public static List<Map<String,String>> toMap(String name,List<String> list){
       return list.stream().map(s -> Collections.singletonMap(name,s)).toList();
    }


}
