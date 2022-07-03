package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;


import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumnFactory;
import com.cl3t4p.progetto.lavoratori2022.model.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.model.repo.LavoratoreRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ModificaController implements Initializable {


    private final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();


    @FXML
    private Button lav_bt;
    @FXML
    private TableData lav_view;
    @FXML
    private TextField nome, cognome, luogo;
    @FXML
    private DatePicker nascita;
    @FXML
    private NumberField id_lav;
    @FXML
    private TableColumn<Map, String> col_id, col_nome, col_cognome, col_luogo, col_data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Disable Aggiungi Lavoratore Button if there are no rows in the table
        lav_bt.disableProperty().bind(lav_view.getSelectionModel().getTableView().getFocusModel().focusedIndexProperty().isEqualTo(-1));

        ButtonColumnFactory factory = new ButtonColumnFactory((lav) -> {
            Main.getDataRepo().setLavoratore_id(Integer.valueOf(lav.get("id")));
            Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
            return null;
        },"*");
        lav_view.setButtonColumn(factory.getNothingColumn());

        //TODO modify supplier add name,surname,birthdate
        lav_view.setSupplier(() -> {
            Lavoratore lavoratore = lavRepo.getLavoratoreByID(id_lav.getValue() == null ? 0 : id_lav.getValue().intValue());
            if (lavoratore == null)
                return List.of();
            return List.of(lavoratore.toMap());
        });

        lav_view.setupColumn(col_id, "id", 30);
        lav_view.setupColumn(col_nome, "nome");
        lav_view.setupColumn(col_cognome, "cognome");
        lav_view.setupColumn(col_luogo, "luogo_nascita");
        lav_view.setupColumn(col_data, "data_nascita");
    }


    @FXML
    private void search(ActionEvent event) {
        lav_view.refreshData();
    }


    @FXML
    private void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }

    public void aggLavoro(ActionEvent actionEvent) {
        Main.getDataRepo().setLavoratore_id(Integer.valueOf(lav_view.getSelectionModel().getTableView().getFocusModel().getFocusedItem().get("id")));
        Main.getLoader().loadView("MENU_LAVORO");
    }
}
