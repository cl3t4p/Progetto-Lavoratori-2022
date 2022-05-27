package progetto.fx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import progetto.Comune;
import progetto.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AddLavController implements Initializable {
    public TextField nome,cognome,luogo_nascita,nazionalita
            ,indirizzo,telefono,email
            ,em_nome,em_cognome,em_telefono,em_email
            ,lingua,id_dipendente,esperienza;
    public ComboBox<String> comune;
    public ChoiceBox<String> patente,automunito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            patente.getItems().addAll(Main.getPostDriver().getPatenti().stream().toList());
//Todo Fix This problem with comune and Text Events
            comune.promptTextProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
            });
            //comune.getItems().addAll(Main.getPostDriver().getComuni().stream().map(Comune::getNome_comune).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void agg_patente(ActionEvent event) {
    }

    public void agg_esperienza(ActionEvent event) {
    }

    public void agg_comune(ActionEvent event) {

    }

    public void agg_lingua(ActionEvent event) {
    }

    public void inserimento_lavoratore(ActionEvent event) {
    }
    public void generatore_id_lav(ActionEvent event) {
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }




    public void comuneSearch(ActionEvent event) throws SQLException {
        List<Comune> comuni = Main.getPostDriver().getComune(comune.getValue(),20);
        comune.getItems().addAll(comuni.stream()
                .map(Comune::getNome_comune)
                .toList());

    }

    public void comune_search(InputMethodEvent event) throws SQLException {
        List<Comune> comuni = Main.getPostDriver().getComune(comune.getValue(),20);
        comune.getItems().addAll(comuni.stream()
                .map(Comune::getNome_comune)
                .toList());
    }
}
