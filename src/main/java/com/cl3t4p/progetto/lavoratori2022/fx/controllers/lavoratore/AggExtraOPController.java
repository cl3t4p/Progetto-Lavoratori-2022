package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.TableData;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AggExtraOPController implements Initializable {


    TableData patenti_data;
    TableData esp_data;
    TableData lingue_data;
    TableData comune_data;

    final PostDriver postDriver = Main.getPostDriver();

    int lavoratore_id;


    @FXML
    Label label_id;




    @FXML
    TableView<Map<String, String>> patenti_view,comuni_view,esp_view,lig_view;
    @FXML
    TableColumn<Map,String> patente_colum,esp_col,lig_col,comuni_col;
    @FXML
    ChoiceBox<String> patente;

    @FXML
    ComboBox<String> comune;
    @FXML
    TextField esperienze,lingue;


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
        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if(!postDriver.delComunebyID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere il comune");
            comune_data.refreshData();
            return null;
        });

        comune_data = new TableData(comuni_view, buttonColumn,()-> TableData.toMap(name,postDriver.getComuniByID(lavoratore_id)));
        comune_data.setupColumn(comuni_col, name);
    }

    public void comune_search(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            List<String> comuni;
            try {
                comuni = postDriver.getComuneILike(comune.getEditor().getText(), 20);
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
            if (postDriver.getComuniByName(comune.getValue()).isEmpty()) {
                JavaFXError.show("Non esiste quel comune!");
                return;
            }
            postDriver.addComuneByID(comune.getValue(), lavoratore_id);
            comune_data.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        comune.setValue("");
    }


    //Lingue

    private void setupLingue() {
        String name = "lingue";
        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if(!postDriver.delLinguaByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere la lingua");
            lingue_data.refreshData();
            return null;
        });

        lingue_data = new TableData(lig_view, buttonColumn,()-> TableData.toMap(name,postDriver.getLingueByID(lavoratore_id)));
        lingue_data.setupColumn(lig_col, name);
    }



    public void addLingua(ActionEvent event) {
        if (lingue.getText().isEmpty()) return;
        try {
            postDriver.addLinguaByID(lavoratore_id, lingue.getText());
            lingue_data.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        lingue.setText("");
    }

    //Esperienze

    private void setupEsp() {
        String name = "esperienze";
        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if(!postDriver.delEspByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile rimuovere l'esperienza");
            esp_data.refreshData();
            return null;
        });

        esp_data = new TableData(esp_view, buttonColumn,()-> TableData.toMap(name,postDriver.getEspByID(lavoratore_id)));
        esp_data.setupColumn(esp_col, name);
    }

    public void addEsp(ActionEvent event) {
        if (esperienze.getText().isEmpty()) return;
        try {
            postDriver.addEspByID(lavoratore_id, esperienze.getText());
            esp_data.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
        esperienze.setText("");
    }

    //Patenti

    private void setupPatenti() {
        String name = "patenti";
        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if(!postDriver.delPatenteByID(lavoratore_id, key.get(name)))
                JavaFXError.DB_ERROR.printContent("Impossibile eliminare la patente");
            patenti_data.refreshData();
            return null;
        });

        patenti_data = new TableData(patenti_view, buttonColumn,()-> TableData.toMap(name,getPatenti()));
        patenti_data.setupColumn(patente_colum, name);

    }

    private List<String> getPatenti()  {
        List<String> full_list = postDriver.getAllPatenti();
        List<String> lav_patenti = postDriver.getPatentiByID(lavoratore_id);

        return full_list.stream()
                .filter(s -> !lav_patenti.contains(s))
                .toList();
    }

    public void addPatente(ActionEvent event) {
        if (patente.getValue() == null) return;
        try {
            postDriver.addPatenteByID(lavoratore_id, patente.getValue());
            patenti_data.refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.show();
        }
    }


    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU_LAVORATORE");
    }
}
