package com.cl3t4p.progetto.lavoratori2022.fx.controllers.view;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.EmergenzaRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class EmeViewController implements Initializable {

    final EmergenzaRepo emeRepo = Main.getRepo().getEmergenzaRepo();

    @FXML
    TableData eme_view;
    @FXML
    Label lav_id;
    @FXML
    TableColumn<Map<String, String>, String> col_nome, col_cognome, col_telefono, col_email;
    @FXML
    TextField em_nome, em_cognome, em_email, em_telefono;

    int lavoratore_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lavoratore_id = Main.getDataRepo().getLavoratore_id();
        lav_id.setText(lav_id.getText() + lavoratore_id);

        eme_view.setupColumn(col_nome, "nome");
        eme_view.setupColumn(col_cognome, "cognome");
        eme_view.setupColumn(col_telefono, "telefono");
        eme_view.setupColumn(col_email, "email");

        eme_view.refreshData(TableData.toMap(emeRepo.getEmergenze(lavoratore_id)));


        eme_view.setupSingleColumn();
        eme_view.setRowFactory(view -> {
            TableRow<Map<String, String>> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    setDate(row.getItem());
                }
            });
            return row;
        });
    }

    private void setDate(Map<String, String> data) {
        em_nome.setText(data.get("nome"));
        em_cognome.setText(data.get("cognome"));
        em_email.setText(data.get("email"));
        em_telefono.setText(data.get("telefono"));
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("VIEW_LAVORATORE");
    }
}
