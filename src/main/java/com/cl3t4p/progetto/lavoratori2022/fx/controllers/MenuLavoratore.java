package com.cl3t4p.progetto.lavoratori2022.fx.controllers;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoro;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuLavoratore implements Initializable {


    private final PostDriver postDriver = Main.getPostDriver();
    private final DataRepo dataRepo = Main.getDataRepo();
    private final ObservableList<Map<String, String>> lavoro_list = FXCollections.observableArrayList();


    @FXML
    private TableView<Map<String, String>> lav_view;

    @FXML
    private DatePicker ini_per,fine_per;

    @FXML
    private TableColumn<Map, String>  col_nome, col_mansione, col_luogo, col_retri,col_ini,col_fine;

    @FXML
    private Label lav_id;

    @FXML
    private TextField nome,luogo,mansione;

    @FXML
    private NumberField retri;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dataRepo.getLavoratore_id() != null) {
            lav_id.setText(lav_id.getText() + dataRepo.getLavoratore_id());
            lav_id.setVisible(true);
        }
        setupLavoro();

    }

    private void setupLavoro() {
        try{
            refreshLavoro();
            lav_view.setItems(lavoro_list);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void refreshLavoro() throws SQLException {
        lavoro_list.clear();
        lavoro_list.addAll(postDriver.getLavoroByLavID(dataRepo.getLavoratore_id()).stream().map(Lavoro::toMap).toList());
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU_LAVORATORE");
    }

    public void addLavoro(ActionEvent event) {
        try {
            if(postDriver.addLavoro(getLavoro()) == -1){
               throw new JavaFXDataError();
            }
        } catch (SQLException | JavaFXDataError e) {
            JavaFXError.DB_ERROR.printContent("Errore nell'inserimento del lavoro");
        }

    }

    private Lavoro getLavoro(){
        Lavoro lavoro = new Lavoro();
        lavoro.setLuogo(luogo.getText());
        lavoro.setMansione(mansione.getText());
        lavoro.setRetribuzione(retri.getValue().intValue());
        lavoro.setInizio_periodo(Date.valueOf(ini_per.getValue()));
        lavoro.setFine_periodo(Date.valueOf(fine_per.getValue()));
        lavoro.setId_lavoratore(dataRepo.getLavoratore_id());
        return lavoro;
    }
}
