package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.FilterCreator;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.model.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.model.PatenteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchController implements Initializable {

    private final PatenteRepo patRepo = Main.getRepo().getPatenteRepo();
    private final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();


    @FXML
    TableData lav_view;

    @FXML
    TableColumn<Map, String> col_id, col_nome, col_cognome, col_nascita, col_residenza;

    @FXML
    DatePicker inizio_dis, fine_dis;

    @FXML
    RadioButton and;

    @FXML
    CheckBox similar;

    @FXML
    ChoiceBox<String> patente, automunito;

    @FXML
    TextField nome, cognome, lingua, mansione, residenza;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patente.getItems().addAll(patRepo.getAllPatenti());
        ButtonColumn column = new ButtonColumn("", (map) -> {
            Main.getDataRepo().setLavoratore_id(Integer.valueOf(map.get("id")));
            Main.getLoader().loadView("VIEWER");
            return null;
        });
        lav_view.setButtonColumn(column);
        lav_view.setupColumn(col_id, "id", 30);
        lav_view.setupColumn(col_nome, "nome");
        lav_view.setupColumn(col_cognome, "cognome");
        lav_view.setupColumn(col_nascita, "nascita");
        lav_view.setupColumn(col_residenza, "indirizzo");


    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }

    public void ricerca(ActionEvent event) {
        Search search = new Search();
        search.setNome(nome.getText());
        search.setCognome(cognome.getText());
        search.setLingua(lingua.getText());
        search.setMansione(mansione.getText());
        search.setResidenza(residenza.getText());
        search.setPatente(patente.getValue());
        search.setAutomunito(automunito.getValue());
        search.setInizio_dis(inizio_dis.getValue());
        search.setFine_dis(fine_dis.getValue());
        search.setAnd(and.isSelected());
        search.setSimilar(similar.isSelected());
        lav_view.refreshData(search
                .search()
                .stream()
                .map(Lavoratore::toMap)
                .toList());
    }

    @Setter
    @Getter
    private class Search {
        private String nome, cognome, lingua, mansione, residenza, patente, automunito;
        private LocalDate inizio_dis, fine_dis;
        private boolean and, similar;

        public List<Lavoratore> search() {
            FilterCreator creator = new FilterCreator();
            creator.setDefaultLogic(and ? FilterCreator.Logic.AND : FilterCreator.Logic.OR);
            creator.setDefaultSimilar(similar);
            creator.addFilter("nome", nome, FilterCreator.TypeVar.STRING);
            creator.addFilter("cognome", cognome, FilterCreator.TypeVar.STRING);
            creator.addFilter("lingua", lingua, FilterCreator.TypeVar.STRING);
            creator.addFilter("esperienza", mansione, FilterCreator.TypeVar.STRING);
            creator.addFilter("indirizzo", residenza, FilterCreator.TypeVar.STRING);
            creator.addFilter("patente", patente, FilterCreator.TypeVar.STRING);
            creator.addFilter("automunito", automunito, FilterCreator.TypeVar.STRING);
            creator.addFilter("inizio_periodo_disp", convertLocalDate(inizio_dis), FilterCreator.TypeVar.DATE);
            creator.addFilter("fine_periodo_disp", convertLocalDate(fine_dis), FilterCreator.TypeVar.DATE);
            return lavRepo.filterLavoratore(creator);
        }


        private String convertLocalDate(LocalDate localDate) {
            if (localDate == null) return null;
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString();
        }
    }
}
