package com.cl3t4p.progetto.lavoratori2022.fx.controllers;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.TableData;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoro;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuLavoro implements Initializable {


    private final PostDriver postDriver = Main.getPostDriver();
    private final DataRepo dataRepo = Main.getDataRepo();
    private TableData tableData;


    @FXML
    private TableView<Map<String, String>> lav_view;

    @FXML
    private DatePicker ini_per,fine_per;

    @FXML
    private TableColumn<Map, String>  col_nome, col_mansione, col_luogo, col_retri,col_ini,col_fine,col_id;

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

        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if(!postDriver.deleteLavoroByID(Integer.valueOf(key.get("id"))))
                JavaFXError.DB_ERROR.printContent("Errore nella cancellazione dell'lavoro");
            tableData.refreshData();
            return null;
        });


        tableData = new TableData(lav_view,buttonColumn,()-> TableData.toMap(postDriver.getLavoroByLavID(dataRepo.getLavoratore_id())));

        tableData.setupColumn(col_id,"id",30);
        tableData.setupColumn(col_nome, "nome_azienda");
        tableData.setupColumn(col_mansione, "mansione");
        tableData.setupColumn(col_luogo, "luogo");
        tableData.setupColumn(col_retri, "retribuzione");
        tableData.setupColumn(col_ini, "inizio_periodo");
        tableData.setupColumn(col_fine, "fine_periodo");

        tableData.refreshData();

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
            e.printStackTrace();
            JavaFXError.DB_ERROR.printContent("Errore nell'inserimento del lavoro");
        }
        tableData.refreshData();
    }

    private Lavoro getLavoro(){
        Lavoro lavoro = new Lavoro();
        lavoro.setLuogo(luogo.getText());
        lavoro.setMansione(mansione.getText());
        lavoro.setNome_azienda(nome.getText());
        lavoro.setRetribuzione(retri.getValue().intValue());
        lavoro.setInizio_periodo(Date.valueOf(ini_per.getValue()));
        lavoro.setFine_periodo(Date.valueOf(fine_per.getValue()));
        lavoro.setId_lavoratore(dataRepo.getLavoratore_id());
        return lavoro;
    }
}
