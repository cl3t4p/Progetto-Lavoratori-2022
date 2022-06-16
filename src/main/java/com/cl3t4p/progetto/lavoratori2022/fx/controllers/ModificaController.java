package com.cl3t4p.progetto.lavoratori2022.fx.controllers;


import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;

import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class ModificaController implements Initializable {


    private final PostDriver postDriver = Main.getPostDriver();


    private final ObservableList<Map<String, String>> lavoratore_list = FXCollections.observableArrayList();

    @FXML
    private TableView<Map<String,String>> lav_view;

    @FXML
    private TextField nome,cognome,luogo;

    @FXML
    private DatePicker nascita;

    @FXML
    private NumberField id_lav;

    @FXML
    private TableColumn<Map,String> col_id,col_nome,col_cognome,col_luogo,col_data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupColumn(col_id, "id");
        setupColumn(col_nome, "nome");
        setupColumn(col_cognome, "cognome");
        setupColumn(col_luogo, "email");
        setupColumn(col_data, "telefono");
        lav_view.setItems(lavoratore_list);
    }


    private void setupColumn(TableColumn<Map, String> column, String name) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(new MapValueFactory<>(name));
    }




    @FXML
    private void search(ActionEvent event) throws SQLException {
        lavoratore_list.clear();
        if(!id_lav.getText().isEmpty()){
            lavoratore_list.add(postDriver.getLavoratoreByID(Math.toIntExact(id_lav.getValue())).toMap());
            return;
        }
    }


    @FXML
    private void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }
}
