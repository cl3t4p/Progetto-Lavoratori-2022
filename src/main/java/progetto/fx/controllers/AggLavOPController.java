package progetto.fx.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import progetto.Main;
import progetto.database.PostDriver;
import progetto.database.exception.JavaFXError;
import progetto.fx.components.ButtonColumn;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AggLavOPController implements Initializable {



    private final ObservableList<String> patenti_list = FXCollections.observableArrayList();
    private final ObservableList<String> lingue_list = FXCollections.observableArrayList();
    private final ObservableList<String> esp_list = FXCollections.observableArrayList();

    private final PostDriver postDriver = Main.getPostDriver();

    private int lavoratore_id;


    public Label label_id;

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
        //setupLingue();

    }
    private void setupLabel(){
        if(lavoratore_id != 0)
            label_id.setText(String.valueOf(lavoratore_id));
    }



    private void setupCol(TableColumn<String, String> column,TableView<String> tableView,ButtonColumn.DB_Exec exec) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue()));
        ButtonColumn buttonColumn = new ButtonColumn("",lavoratore_id,exec);
        column.prefWidthProperty().bind(tableView.widthProperty().subtract(buttonColumn.widthProperty()).subtract(2));
        tableView.getColumns().add(buttonColumn.clone());
    }



    //Esperienze

    private void setupLingue() {
        ButtonColumn.DB_Exec exec = ((lav_id, key) -> {
            postDriver.delLinguaByID(lav_id,key);
            refreshPatenteList();
        });
        setupCol(lig_col,lig_view,exec);
        try {
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

    //Patenti
    private void setupPatenti(){
        patente_colum.setEditable(false);
        patente_colum.setResizable(false);
        patente_colum.setReorderable(false);
        patente_colum.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue()));
       // setupCol(patente_colum,patenti_view,exec);
        try {
            refreshPatenteList();
            patenti_view.setItems(patenti_list);
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.fxMSG();
        }
        ButtonColumn buttonColumn = new ButtonColumn("",lavoratore_id,(lav_id, key) -> {
            postDriver.delPatenteByID(lav_id,key);
            refreshPatenteList();
        });
        System.out.println(buttonColumn.widthProperty());

        System.out.println(patente_colum.widthProperty());

        patente_colum.prefWidthProperty().bind(patenti_view.widthProperty().subtract(buttonColumn.widthProperty()).subtract(2));
        patenti_view.getColumns().add(buttonColumn);
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



}
