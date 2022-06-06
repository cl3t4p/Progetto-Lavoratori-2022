package com.cl3t4p.progetto.Lavoratori2022.fx.controllers;

import com.cl3t4p.progetto.Lavoratori2022.Main;
import com.cl3t4p.progetto.Lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.Lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.Lavoratori2022.fx.components.ButtonColumn;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AggLavOPController implements Initializable {



    private final ObservableList<String> patenti_list = FXCollections.observableArrayList();
    private final ObservableList<String> lingue_list = FXCollections.observableArrayList();
    private final ObservableList<String> esp_list = FXCollections.observableArrayList();
    private final ObservableList<String> comune_list = FXCollections.observableArrayList();

    private final PostDriver postDriver = Main.getPostDriver();

    private int lavoratore_id;



    public Label label_id;

    public TableView<String> comuni_view;
    public TableColumn<String,String> comuni_col;
    public ComboBox<String> comune;

    public TableView<String> patenti_view;
    public TableColumn<String,String> patente_colum;
    public ChoiceBox<String> patente;


    public TableView<String> esp_view;
    public TableColumn<String,String> esp_col;
    public TextField esperienze;

    public TableColumn<String,String> lig_col;
    public TableView<String> lig_view;
    public TextField lingue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lavoratore_id = Main.getDataRepo().getLavoratore_id();
        setupLabel();
        setupPatenti();
        setupLingue();
        setupEsp();
        setupComune();
    }
    private void setupLabel(){
        if(lavoratore_id != 0)
            label_id.setText(String.valueOf(lavoratore_id));
    }



    private void setupCol(TableColumn<String, String> column, TableView<String> tableView, ButtonColumn.DB_Exec exec) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue()));
        ButtonColumn buttonColumn = new ButtonColumn("",lavoratore_id,exec);
        column.prefWidthProperty().bind(tableView.widthProperty().subtract(buttonColumn.widthProperty()).subtract(2));
        tableView.getColumns().add(buttonColumn.clone());
    }

    //Comuni

    public void setupComune(){
        ButtonColumn.DB_Exec exec = ((lav_id, key) -> {
            postDriver.delComunebyID(lav_id,key);
            refreshPatenteList();
        });
        setupCol(comuni_col,comuni_view,exec);
        try {
            comuni_view.setItems(comune_list);
            refreshComuni();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
    }

    public void refreshComuni() throws SQLException {
        List<String> full_list = postDriver.getComuniByID(lavoratore_id);
        comune_list.clear();
        comune_list.addAll(full_list);
    }

    public void comune_search(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            List<String> comuni;
            try {
                comuni = postDriver.getComuneILike(comune.getEditor().getText(),20);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            comune.getItems().clear();
            comune.getItems().addAll(comuni);
            comune.show();
        }
    }

    public void addComune(ActionEvent event){
        if(comune.getValue().isEmpty())return;
        try {
            if(postDriver.getComuniByName(comune.getValue()).isEmpty()) {
                JavaFXError.fxErrorMSG("Non esiste quel comune!");
                return;
            }
            postDriver.addComuneByID(comune.getValue(),lavoratore_id);
            refreshComuni();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
        comune.setValue("");
    }




    //Lingue

    private void setupLingue() {
        ButtonColumn.DB_Exec exec = ((lav_id, key) -> {
            postDriver.delLinguaByID(lav_id,key);
            refreshPatenteList();
        });
        setupCol(lig_col,lig_view,exec);
        try {
            lig_view.setItems(lingue_list);
            refreshLingue();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
    }

    private void refreshLingue() throws SQLException{
        List<String> full_list = postDriver.getLingueByID(lavoratore_id);
        lingue_list.clear();
        lingue_list.addAll(full_list);
    }

    public void addLingua(ActionEvent event) {
        if(lingue.getText().isEmpty())return;
        try {
            postDriver.addLinguaByID(lavoratore_id,lingue.getText());
            refreshLingue();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
        lingue.setText("");
    }

    //Esperienze

    private void setupEsp() {
        ButtonColumn.DB_Exec exec = ((lav_id, key) -> {
            postDriver.delEspByID(lav_id,key);
            refreshPatenteList();
        });
        setupCol(esp_col,esp_view,exec);
        try {
            esp_view.setItems(esp_list);
            refreshEsp();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
    }

    public void addEsp(ActionEvent event) {
        if(esperienze.getText().isEmpty())return;
        try {
            postDriver.addEspByID(lavoratore_id,esperienze.getText());
            refreshEsp();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
        esperienze.setText("");
    }

    private void refreshEsp() throws SQLException {
        List<String> full_list = postDriver.getEspByID(lavoratore_id);
        esp_list.clear();
        esp_list.addAll(full_list);
    }

    //Patenti

    private void setupPatenti(){
        ButtonColumn.DB_Exec exec = (lav_id, key) -> {
            postDriver.delPatenteByID(lav_id,key);
            refreshPatenteList();
        };
       setupCol(patente_colum,patenti_view,exec);
        try {
            refreshPatenteList();
            patenti_view.setItems(patenti_list);
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
    }

    private void refreshPatenteList() throws SQLException {
        List<String> full_list = postDriver.getAllPatenti();
        Set<String> lav_patenti = postDriver.getPatentiByID(lavoratore_id);

        patente.getItems().clear();
        patente.getItems().addAll(
                full_list.stream()
                        .filter(s -> !lav_patenti.contains(s))
                        .toList());
        patenti_list.clear();
        patenti_list.addAll(lav_patenti);
    }

    public void addPatente(ActionEvent event) {
        if(patente.getValue() == null)return;
        try {
            postDriver.addPatenteByID(lavoratore_id,patente.getValue());
            refreshPatenteList();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
    }




    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }
}
