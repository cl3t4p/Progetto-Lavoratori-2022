package com.cl3t4p.progetto.lavoratori2022.fx.controllers.view;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.ComuneRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.EsperienzaRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.LinguaRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.PatenteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpzViewModeController implements Initializable {

    final PatenteRepo patRepo = Main.getRepo().getPatenteRepo();
    final LinguaRepo ligRepo = Main.getRepo().getLinguaRepo();
    final EsperienzaRepo espRepo = Main.getRepo().getEsperienzaRepo();
    final ComuneRepo comuneRepo = Main.getRepo().getComuneRepo();

    @FXML
    TextField esperienze, lingue, comune, patente;
    @FXML
    Label label_id;
    @FXML
    TableData patenti_view, esp_view, lig_view, comuni_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int lavoratore_id = Main.getMemRepo().getLavoratore_id();
        label_id.setText(String.valueOf(lavoratore_id));

        setupRow(patenti_view, patente);
        setupRow(esp_view, esperienze);
        setupRow(lig_view, lingue);
        setupRow(comuni_view, comune);

        patenti_view.refreshData(TableData.toMap("default", patRepo.getPatentiByID(lavoratore_id)));
        esp_view.refreshData(TableData.toMap("default", espRepo.getEspByID(lavoratore_id)));
        lig_view.refreshData(TableData.toMap("default", ligRepo.getLingueByID(lavoratore_id)));
        comuni_view.refreshData(TableData.toMap("default", comuneRepo.getComuniByID(lavoratore_id)));
    }

    private void setupRow(TableData table, TextField text) {
        table.setupSingleColumn();
        table.setRowFactory(view -> {
            TableRow<Map<String, String>> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    text.setText(row.getItem().get("default"));
                }
            });
            return row;
        });
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("VIEW_LAVORATORE");
    }
}
