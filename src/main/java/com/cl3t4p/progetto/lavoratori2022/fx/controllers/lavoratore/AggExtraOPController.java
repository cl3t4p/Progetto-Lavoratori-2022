package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.model.repo.ComuneRepo;
import com.cl3t4p.progetto.lavoratori2022.model.repo.EsperienzaRepo;
import com.cl3t4p.progetto.lavoratori2022.model.repo.LinguaRepo;
import com.cl3t4p.progetto.lavoratori2022.model.repo.PatenteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AggExtraOPController implements Initializable {

    final PatenteRepo patRepo = Main.getRepo().getPatenteRepo();
    final LinguaRepo ligRepo = Main.getRepo().getLinguaRepo();
    final EsperienzaRepo espRepo = Main.getRepo().getEsperienzaRepo();
    final ComuneRepo comuneRepo = Main.getRepo().getComuneRepo();

    int lavoratore_id;

    @FXML
    Label label_id;

    @FXML
    TableData patenti_view, esp_view, lig_view, comuni_view;

    @FXML
    TableColumn<Map, String> patente_colum, esp_col, lig_col, comuni_col;
    @FXML
    ChoiceBox<String> patente;

    @FXML
    ComboBox<String> comune;

    @FXML
    TextField esperienze, lingue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lavoratore_id = Main.getDataRepo().getLavoratore_id();


        setupLabel();
        setupPatenti();
        setupLingue();
        setupEsp();
        setupComune();
    }

    private void setupLabel() {
        if (lavoratore_id != 0)
            label_id.setText(String.valueOf(lavoratore_id));
    }


    //Comuni

    public void setupComune() {
        String name = "comune";
        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(key -> {
            if (!comuneRepo.delComunebyID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere il comune");
            comuni_view.refreshData();
            return null;
        });
        comuni_view.setSupplier(() -> TableData.toMap(name, comuneRepo.getComuniByID(lavoratore_id)));
        comuni_view.setButtonColumn(factory.getCellFactory(ColumnAction.REMOVE));
        comuni_view.setupColumn(comuni_col, name);
    }

    public void comune_search(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            List<String> comuni;
            try {
                comuni = comuneRepo.getComuneILike(comune.getEditor().getText(), 20);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            comune.getItems().clear();
            comune.getItems().addAll(comuni);
            comune.show();
        }
    }

    public void addComune(ActionEvent event) {
        if (comune.getValue().isEmpty()) return;
        try {
            if (comuneRepo.getComuniByName(comune.getValue()).isEmpty()) {
                JavaFXError.show("Non esiste quel comune!");
                return;
            }
            comuneRepo.addComuneByID(comune.getValue(), lavoratore_id);
            comuni_view.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        comune.setValue("");
    }


    //Lingue

    private void setupLingue() {
        String name = "lingue";
        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(key -> {
            if (!ligRepo.delLinguaByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere la lingua");
            lig_view.refreshData();
            return null;
        });

        lig_view.setButtonColumn(factory.getCellFactory(ColumnAction.REMOVE));
        lig_view.setSupplier(() -> TableData.toMap(name, ligRepo.getLingueByID(lavoratore_id)));
        lig_view.setupColumn(lig_col, name);
    }


    public void addLingua(ActionEvent event) {
        if (lingue.getText().isEmpty()) return;
        try {
            ligRepo.addLinguaByID(lavoratore_id, lingue.getText());
            lig_view.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        lingue.setText("");
    }

    //Esperienze

    private void setupEsp() {
        String name = "esperienze";
        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(key -> {
            if (!espRepo.delEspByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere l'esperienza");
            esp_view.refreshData();
            return null;
        });

        esp_view.setButtonColumn(factory.getCellFactory(ColumnAction.REMOVE));
        esp_view.setSupplier(() -> TableData.toMap(name, espRepo.getEspByID(lavoratore_id)));
        esp_view.setupColumn(esp_col, name);
    }

    public void addEsp(ActionEvent event) {
        if (esperienze.getText().isEmpty()) return;
        try {
            espRepo.addEspByID(lavoratore_id, esperienze.getText());
            esp_view.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        esperienze.setText("");
    }

    //Patenti

    private void setupPatenti() {
        updatePatenteItems();
        String name = "patenti";
        CellButtonFactoryFactory buttonColumn = new CellButtonFactoryFactory(key -> {
            if (!patRepo.delPatenteByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile eliminare la patente");
            patenti_view.refreshData();
            return null;
        });

        patenti_view.setButtonColumn(buttonColumn.getCellFactory(ColumnAction.REMOVE));
        patenti_view.setSupplier(() -> TableData.toMap(name, patRepo.getPatentiByID(lavoratore_id)));
        patenti_view.setupColumn(patente_colum, name);

    }

    private void updatePatenteItems() {
        List<String> full_list = patRepo.getAllPatenti();
        List<String> lav_patenti = patRepo.getPatentiByID(lavoratore_id);

        patente.getItems().clear();
        patente.getItems().addAll(full_list.stream()
                .filter(s -> !lav_patenti.contains(s))
                .toList());
    }

    public void addPatente(ActionEvent event) {
        if (patente.getValue() == null) return;
        try {
            patRepo.addPatenteByID(lavoratore_id, patente.getValue());
            patenti_view.refreshData();
            updatePatenteItems();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
    }


    public void back(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
    }
}
