package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.model.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.model.repo.PatenteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchController implements Initializable {

    final PatenteRepo patRepo = Main.getRepo().getPatenteRepo();
    final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();

    FilterBuilder builder = new FilterBuilder();

    @FXML
    Button bt_nome,bt_cognome,bt_lingua,bt_residenza,bt_patente,bt_automunito,bt_esperienza,bt_data;
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
    TextArea filters;
    @FXML
    TextField nome, cognome, lingua, esperienza, residenza;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patente.getItems().addAll(patRepo.getAllPatenti());
        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(map -> {
            Main.getDataRepo().setLavoratore_id(Integer.valueOf(map.get("id")));
            Main.getDataRepo().setViewMode(true);
            //Add View Mode to the fxml files
            Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
            return null;
        },"+");


        lav_view.setButtonColumn(factory.getCellFactory(ColumnAction.NOTHING));
        lav_view.setupColumn(col_id, "id", 50);
        lav_view.setupColumn(col_nome, "nome");
        lav_view.setupColumn(col_cognome, "cognome");
        lav_view.setupColumn(col_nascita, "nascita");
        lav_view.setupColumn(col_residenza, "indirizzo");

        bt_automunito.setOnAction((e)-> addFilter("automunito", FilterBuilder.TypeVar.STRING,automunito.getValue()));
        bt_patente.setOnAction((e)->addFilter("nome_patente", FilterBuilder.TypeVar.STRING,patente.getValue()));
        bt_nome.setOnAction((e)-> addFilter("nome", FilterBuilder.TypeVar.STRING,nome.getText()));
        bt_cognome.setOnAction((e)-> addFilter("cognome", FilterBuilder.TypeVar.STRING,cognome.getText()));
        bt_lingua.setOnAction((e)-> addFilter("nome_lingua", FilterBuilder.TypeVar.STRING,lingua.getText()));
        bt_esperienza.setOnAction(e -> addFilter("esperienza", FilterBuilder.TypeVar.STRING,esperienza.getText()));
        bt_residenza.setOnAction(e-> addFilter("nome_comune", FilterBuilder.TypeVar.STRING,residenza.getText()));
        bt_data.setOnAction(this::addDataToFilter);


    }

    private void addDataToFilter(ActionEvent event) {
        LocalDate inizio = inizio_dis.getValue();
        LocalDate fine = fine_dis.getValue();
        if(inizio != null && fine != null){
            if(inizio.isAfter(fine)){
                addFilter("inizio_periodo_disp ", FilterBuilder.TypeVar.DATE, String.valueOf(inizio));
                builder.addFilter("fine_periodo_disp ", String.valueOf(fine), FilterBuilder.TypeVar.DATE, FilterBuilder.Logic.AND,false);
                filters.setText(builder.readableString());
                search();
            }else
                JavaFXError.INVALID_DATE.printContent("La data di inizio deve essere precedente a quella di fine");
        }else
            JavaFXError.INVALID_DATE.printContent("Inserisci delle date valide");
    }

    private void addFilter(String nome, FilterBuilder.TypeVar type, String value) {
        builder.addFilter(nome,value,type,and.isSelected() ? FilterBuilder.Logic.AND : FilterBuilder.Logic.OR,similar.isSelected());
        filters.setText(builder.readableString());
        search();
    }

    private void search(){
        lav_view.refreshData(lavRepo.filterLavoratore(builder)
                .stream()
                .map(Mappable::toMap)
                .toList());
    }


    public void ricerca(ActionEvent event) {
        builder = new FilterBuilder();
        filters.setText(builder.readableString());
    }


    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }

}
