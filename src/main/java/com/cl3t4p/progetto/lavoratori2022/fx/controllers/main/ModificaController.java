package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;


import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.functions.Mappable;
import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.database.filter.PostFilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.fx.components.numbertf.LongTextField;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class ModificaController implements Initializable {


    private final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();


    @FXML
    private Button lav_bt;
    @FXML
    private TableData lav_view;
    @FXML
    private TextField nome, cognome, luogo, nazionalita;
    @FXML
    private LongTextField id_lav;
    @FXML
    private TableColumn<Map<String, String>, String> col_id, col_nome, col_cognome, col_luogo, col_data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Disable Aggiungi Lavoratore Button if there are no rows in the table
        lav_bt.disableProperty().bind(lav_view.getSelectionModel().getTableView().getFocusModel().focusedIndexProperty().isEqualTo(-1));

        CellButtonFactoryFactory factory = new CellButtonFactoryFactory((lav) -> {
            Main.getDataRepo().setLavoratore_id(Integer.valueOf(lav.get("id")));
            Main.getLoader().loadView("LAVORATORE");
            return null;
        }, "*");
        lav_view.setButtonColumn(factory.getCellFactory(ColumnAction.NOTHING));

        lav_view.setSupplier(this::getLavoratori);
        lav_view.setupColumn(col_id, "id", 50);
        lav_view.setupColumn(col_nome, "nome");
        lav_view.setupColumn(col_cognome, "cognome");
        lav_view.setupColumn(col_luogo, "luogo_nascita");
        lav_view.setupColumn(col_data, "data_nascita");
    }

    private List<Map<String, String>> getLavoratori() {
        List<Lavoratore> list = new ArrayList<>();
        if (!id_lav.getText().isBlank()) {
            list.add(lavRepo.getLavoratoreByID(id_lav.getValue().intValue()));
        } else {
            FilterBuilder builder = new PostFilterBuilder(FilterBuilder.Logic.AND, true);
            builder.addFilter("nome", nome.getText(), FilterBuilder.TypeVar.STRING);
            builder.addFilter("cognome", cognome.getText(), FilterBuilder.TypeVar.STRING);
            builder.addFilter("luogo_nascita", luogo.getText(), FilterBuilder.TypeVar.STRING);
            builder.addFilter("nazionalita", nazionalita.getText(), FilterBuilder.TypeVar.STRING);
            list = lavRepo.filterLavoratore(builder);
        }
        if(list.isEmpty())
            return List.of();
        return list.stream()
                .filter(Objects::nonNull)
                .map(Mappable::toMap)
                .toList();
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
