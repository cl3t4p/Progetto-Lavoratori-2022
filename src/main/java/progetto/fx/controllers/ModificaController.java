package progetto.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import progetto.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificaController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private void ricerca_lav(ActionEvent event) {
        Main.getLoader().loadView("RICERCA_LAVORATORE");
    }
    @FXML
    private void aggiungi_lavoratore(ActionEvent event) {
        Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
    }
    @FXML
    private void modifica_anagrafica(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_ANAGRAFICA");
    }
}
