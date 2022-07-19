package com.cl3t4p.progetto.lavoratori2022.fx.components.table;

import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ButtonColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.ICellButtonFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


/***
 * TableData help create and manage a table view that can be used to display a list of objects or a list of maps.
 */
public class TableData extends TableView<Map<String, String>> {
    private final ObservableList<Map<String, String>> list = FXCollections.observableArrayList();

    private Supplier<List<Map<String, String>>> supplier;


    // A list of columns that will have a custom width.
    private final List<Double> customSize = new ArrayList<>();

    public TableData() {
        setItems(list);
    }


    public void setSupplier(Supplier<List<Map<String, String>>> supplier) {
        this.supplier = supplier;
    }

    public void setButtonColumn(ICellButtonFactory buttonFactory) {
        ButtonColumn column = new ButtonColumn(buttonFactory);
        getColumns().add(column);
        customSize.add(column.getWidth() + 3);
    }

    /**
     * Update the table view with the list of maps.
     */
    public void refreshData(List<Map<String, String>> data) {
        list.clear();
        list.addAll(data);
    }

    /**
     * Update the table view with the list called by the supplier.
     */
    public void refreshData() {
        if (supplier != null)
            refreshData(supplier.get());
    }


    /**
     * Prepare a column for the table view.
     *
     * @param column the column to prepare
     * @param key    The key of the map to use as value for the column.
     */
    public void setupColumn(TableColumn<Map<String, String>, String> column, String key) {
        setupColumnOpt(column, key);
        column.prefWidthProperty().bind(
                widthProperty()
                        .subtract(customSize.stream().mapToDouble(Double::doubleValue).sum())
                        .divide(getColumns().size() - customSize.size()));
    }

    public void setupColumn(TableColumn<Map<String, String>, String> column, String key, int size) {
        setupColumnOpt(column, key);
        if (size != -1)
            column.setPrefWidth(size);
        customSize.add(column.getWidth());
    }

    private void setupColumnOpt(TableColumn<Map<String, String>, String> column, String key) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);

        //This will make
        column.setCellFactory(column1 -> new DataTableCell());
        column.setCellValueFactory(new MapStringFactory(key));
        //While hover on cell show the value in the tooltip.
        refreshData();
    }

    @SuppressWarnings("unchecked")
    public void setupSingleColumn() {
        TableColumn<Map<String, String>, String> first = (TableColumn<Map<String, String>, String>) getColumns().get(0);
        setupColumn(first, "default");
    }


    /**
     * Get the list of maps from a mappable objects.
     *
     * @param mappables the list of mappable objects
     * @return the list of maps
     */
    public static List<Map<String, String>> toMap(List<? extends Mappable> mappables) {
        return mappables.stream().map(Mappable::toMap).toList();
    }

    /**
     * Return the list of maps with the same name of the columns.
     *
     * @param name the name of the columns
     * @param list the list of mappable objects
     * @return the list of maps
     */
    public static List<Map<String, String>> toMap(String name, List<String> list) {
        return list.stream().map(s -> Collections.singletonMap(name, s)).toList();
    }


}
