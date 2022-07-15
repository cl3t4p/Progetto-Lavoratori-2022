package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.Mappable;
import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.PatenteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchController implements Initializable {

    final PatenteRepo patRepo = Main.getRepo().getPatenteRepo();
    final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();


    FilterBuilder builder = Main.getRepo().getFilterBuilderInstance();

    @FXML
    Button bt_nome, bt_cognome, bt_lingua, bt_comune, bt_patente, bt_automunito, bt_esperienza, bt_data;
    @FXML
    TableData lav_view;
    @FXML
    HBox radio_buttons;
    @FXML
    TableColumn<Map<String, String>, String> col_id, col_nome, col_cognome, col_nascita, col_residenza;
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
    TextField nome, cognome, lingua, esperienza, comune;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> patenti = patRepo.getAllPatenti();
        patente.getItems().addAll(patenti);
        patente.setValue(patenti.get(0));


        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(map -> {
            Main.getDataRepo().setLavoratore_id(Integer.valueOf(map.get("id")));
            Main.getLoader().loadView("VIEW_LAVORATORE");
            return null;
        }, "+");


        lav_view.setButtonColumn(factory.getCellFactory(ColumnAction.NOTHING));
        lav_view.setupColumn(col_id, "id", 50);
        lav_view.setupColumn(col_nome, "nome");
        lav_view.setupColumn(col_cognome, "cognome");
        lav_view.setupColumn(col_nascita, "data_nascita");
        lav_view.setupColumn(col_residenza, "indirizzo");

        bt_automunito.setOnAction(e -> addFilter("automunito", FilterBuilder.TypeVar.STRING, automunito.getValue()));
        bt_patente.setOnAction(e -> addFilter("nome_patente", FilterBuilder.TypeVar.STRING, patente.getValue()));

        bt_nome.setOnAction(e -> addFilter("nome", nome));
        bt_cognome.setOnAction(e -> addFilter("cognome", cognome));
        bt_lingua.setOnAction(e -> addFilter("nome_lingua", lingua));
        bt_esperienza.setOnAction(e -> addFilter("esperienza", esperienza));
        bt_comune.setOnAction(e -> addFilter("comune", comune));

        bt_data.setOnAction(this::addDataToFilter);


    }

    private void addFilter(String nome, TextField field) {
        addFilter(nome, FilterBuilder.TypeVar.STRING, field.getText());
        field.clear();
    }

    private void addFilter(String nome, FilterBuilder.TypeVar type, String value) {
        builder.addFilter(nome, value, type, and.isSelected() ? FilterBuilder.Logic.AND : FilterBuilder.Logic.OR, similar.isSelected());
        filters.setText(builder.readableString());
        search();
        if (radio_buttons.isDisable())
            radio_buttons.setDisable(false);
    }


    private void addDataToFilter(ActionEvent event) {
        LocalDate inizio = inizio_dis.getValue();
        LocalDate fine = fine_dis.getValue();
        if (inizio != null && fine != null) {
            if (inizio.isBefore(fine)) {
                addFilter("inizio_periodo_disp ", FilterBuilder.TypeVar.DATE, String.valueOf(inizio));
                builder.addFilter("fine_periodo_disp ", String.valueOf(fine), FilterBuilder.TypeVar.DATE, FilterBuilder.Logic.AND, false);
                filters.setText(builder.readableString());
                search();
            } else
                JavaFXError.INVALID_DATE.printContent("La data di inizio deve essere precedente a quella di fine");
        } else
            JavaFXError.INVALID_DATE.printContent("Inserisci delle date valide");
        inizio_dis.getEditor().clear();
        fine_dis.getEditor().clear();
    }


    private void search() {
        lav_view.refreshData(lavRepo.filterLavoratore(builder)
                .stream()
                .map(Mappable::toMap)
                .toList());
    }


    public void remove(ActionEvent event) {
        builder.removeLast();
        filters.setText(builder.readableString());
        if (builder.isEmpty())
            radio_buttons.setDisable(true);
        search();
    }

    public void clear(ActionEvent event) {
        builder = Main.getRepo().getFilterBuilderInstance();
        filters.setText(builder.readableString());
        radio_buttons.setDisable(true);
        search();
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }

}
